package treecalc.comp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;


import treecalc.comp.java.JavaConstants;
import treecalc.comp.java.JavaDiv;
import treecalc.comp.java.JavaFunctions;
import treecalc.comp.java.JavaInput;
import treecalc.comp.java.JavaNodes;
import treecalc.comp.java.JavaTables;
import treecalc.comp.TcSimpleLexer;
import treecalc.comp.TcSimpleParser;
import treecalc.comp.TcSimpleParser.compilationunit_return;
import treecalc.comp.vm.VmFormula;
import treecalc.comp.vm.VmFunctions;
import treecalc.comp.vm.VmInput;
import treecalc.comp.vm.VmNodes;
import treecalc.comp.vm.VmTables;
import treecalc.vm.TciAction;
import treecalc.vm.asm.TciAsmReaderWriter;
import treecalc.vm.asm.TciAssembler;
import treecalc.vm.asm.TciDisassembler;
import treecalc.vm.asm.TciLexer;
import treecalc.vm.asm.TciParser;
import treecalc.vm.asm.TciAssembler.Asm;

public class Action {

	/* TODO: compile on the fly without java source files */
	/*
	 * TODO: generate class files directly or via java bytecode assembler for
	 * initialization parts
	 */
	private static ModelSimple model;

	private static void parseTextFile(String filename, boolean writeast, boolean walkit, String packagename,
			String genpath, boolean gwt, boolean trace) throws IOException, RecognitionException {
		ANTLRFileStream fs = new ANTLRFileStream(filename);
		TcSimpleLexer lexer = new TcSimpleLexer(fs);
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		// for(;;) {
		// tokens.consume();
		// int i = tokens.LA(1);
		// if (i==CommonToken.EOF) {
		// break;
		// }
		// System.out.println(i + ": " + TcSimpleParser.tokenNames[i]);
		// }

		TcSimpleParser parser = new TcSimpleParser(tokens);
		parser.setTreeAdaptor(new TcAstAdapter());
		compilationunit_return x = parser.compilationunit();
		TcAst tree = (TcAst) x.getTree();
		if (writeast) {
			PrintWriter writer = new PrintWriter(filename + ".ast");
			writer.append(tree.toStringTree());
			writer.close();
		}
		if (walkit) {
			String outpath = genpath != null && genpath.length() > 0 ? genpath + "/" : "c:/temp/temp/gen/";

			ModelSimple model = new ModelSimple(tree);

			FillModelStep0_Nodes.build(model);
			FillModelStep1_Symboltables.build(model);
			FillModelStep2_ResolveNames.build(model);

			GenFill.fillModel(model);

			JavaNodes.generate(model, outpath, packagename, gwt, trace);
			JavaInput.generate(model, outpath, packagename, gwt, trace);
			JavaDiv.generate(model, outpath, packagename, gwt, trace);
			JavaTables.generate(model, outpath, packagename, gwt, trace);
			JavaFunctions.generate(model, outpath, packagename, trace);
			JavaConstants.generate(model, outpath, packagename);

			String filenameTc = outpath + "/" + packagename ;
			new File(filenameTc + ".tci").delete();
			VmNodes.generate(model, outpath, packagename);
			VmTables.generate(model, outpath, packagename);
			VmFunctions.generate(model, outpath, packagename);
			VmInput.generate(model, outpath, packagename);
			VmFormula.generate(model, outpath, packagename);
			
			
			ANTLRFileStream fsTci = new ANTLRFileStream(filenameTc + ".tci"); 
			TciLexer lexerTci = new TciLexer(fsTci);
			CommonTokenStream tokensTci = new CommonTokenStream(lexerTci);
			TciAssembler assemblerTci = new TciAssembler();
			TciParser parserTci = new TciParser(tokensTci, assemblerTci);
			parserTci.tci();
			System.out.println(TciAction.class.getName() + ": done.");
			Asm asm = assemblerTci.getAsm();
			TciAsmReaderWriter.write(asm, filenameTc + ".tcx");

			// FillModelStep3.generate(tree, "c:/temp/temp/gen/");
			// model.writeToFile(new File(filename + ".tcx"));

			// PmsWalkerManual.walk(tree, filename+ ".tcs");
			// TreeNodeStream treenodestream = new CommonTreeNodeStream(tree);
			// PmsWalker1 walker1 = new PmsWalker1(treenodestream);
			// walker1.compilationunit();

			// TreeNodeStream treenodestream2 = new CommonTreeNodeStream(tree);
			// PmsTreeRewrite rewriter = new PmsTreeRewrite(treenodestream2);
			// tree = (CommonTree) rewriter.downup(tree, true);
			// System.out.println(tree.toStringTree());
		}
		// for(;;) {
		// tokens.consume();
		// int token = tokens.LA(1);
		// if (token==Token.EOF) {
		// break;
		// }
		// }
	}

	public static void doit(String filenamePmt, boolean writeast, boolean walkit, String packagename, String genpath,
			boolean packetize, String jarName, boolean trace) throws IOException {
		System.out.println("start parsing " + filenamePmt);
		long start = System.currentTimeMillis();
		// System.out.println(new File(".").getAbsolutePath());
		try {
			parseTextFile(filenamePmt, writeast, walkit, packagename, genpath, false, trace); //non-gwt
		} catch (RecognitionException e) {
			System.err.println("error in " + filenamePmt + ", line " + e.line + ", pos " + e.charPositionInLine);
			throw new RuntimeException(e);
		}
		long end = System.currentTimeMillis();
		long sec = (end - start) / 1000;
		System.out.println("done with " + filenamePmt + " in " + sec + " sec.");
		if (packetize) {
			compileAndPacketize(genpath, jarName);
		}
	}

	static void doittestgen(String modelname) throws IOException {
		doit(Constants.TCS_INPUT_PATH + modelname + ".tcs", false, true, "gen." + modelname.toLowerCase(),
			Constants.GENERATED_TESTSOURCE_OUTPUT_PATH + modelname.toLowerCase(), false, null, false);
	}

	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			printUseUsage();
		} else if (args.length == 1) {
			if (args[0].startsWith("-")) {
				if (args[0].equalsIgnoreCase("-usage")) {
					System.out.println("Usage:");
					System.out.println("-version ... prints Version number");
					System.out.println("-usage ... prints usage");
					System.out.println("(filename.tcs [jarname])* ... you can convert *.tcs files as much as you like, optinal you can add to every *.tcs file the name of the resulting jar");
					System.out.println("Usage");
					System.out.println("Usage");
					/* TODO */
				} else if (args[0].equalsIgnoreCase("-mavenunittests")) {
					List<String> listOfModelNames = getTestModelNames();
					for (String s : listOfModelNames) {
						doittestgen(s);
					}
				} else if (args[0].equalsIgnoreCase("-version")) {
					System.out.println("Version " + getVersionFromMaven());
				}
			} else if (checkTcsFileString(args[0])) {
				if (checkFileExist(args[0])) {
					String modelName = null;
					if (args[0].indexOf("/") != -1) {
						modelName = args[0].split("/")[args[0].split("/").length - 1].replace(".tcs", "");
					} else {
						modelName = args[0].replace(".tcs", "");
					}

					doit(args[0], false, true, "gen." + modelName.toLowerCase(), modelName.toLowerCase(), true, null, false);
				} else {
					System.out.println("File " + args[0] + " does not exist.");
				}
			} else {
				printUseUsage();
			}
		} else if (checkTcsFileString(args[0])) {
			List<String[]> list = new ArrayList<String[]>();
			for (int i = 0; i < args.length; i++) {
				if (checkFileExist(args[i])) {
					String modelName = null;
					if (args[0].indexOf("/") != -1) {
						modelName = args[i].split("/")[args[i].split("/").length - 1].replace(".tcs", "");
					} else {
						modelName = args[i].replace(".tcs", "");
					}
					if (checkTcsFileString(args[i]) && !(args.length == i + 1) && !checkTcsFileString(args[i + 1])) {
						String jarName = args[i + 1];
						list.add(new String[] { args[i], modelName.toLowerCase(), jarName.toLowerCase() });
						i++;
					} else {
						list.add(new String[] { args[i], modelName.toLowerCase(), null });
					}
				} else {
					System.out.println("File " + args[i] + " does not exist.");
					return;
				}
			}

			for (String[] listEntry : list) {
				doit(listEntry[0], false, true, "gen." + listEntry[1], listEntry[1], true, listEntry[2], false);
			}

		} else {
			printUseUsage();
		}
		// doitgen("testinput");
		// doitgen("URO_LI_ENDOW");
		// doitgen("U_BUFT_VIP_CLIENT");
		// doitgen("AWXXX");
		// doitgen("HWH11");
		// doitgen("HWK11");
		// doitgen("HWU10");
		// doitgen("ARXXX");
		// doitgen("AQXXX");
		// doitgen("UV");
		// doitgen("APXXX");
		// doit("src/com/hackhofer/treecalc/parser/antlr/simple/test/sv/sv.pmt.tcs",
		// false, true, "com.hackhofer.treecalc.parser.antlr.simple.test.sv",
		// "c:/ws-treecalc/com.hackhofer.treecalc.model/src/com/hackhofer/treecalc/parser/antlr/simple/test/sv");
		// doit("c:/ws-treecalc/GeneratedModels/uro_li_endow.pmt.tcs", false,
		// true, "com.hackhofer.treecalc.parser.antlr.simple.test.uro_endow",
		// "c:/ws-treecalc/com.hackhofer.treecalc.model/src/com/hackhofer/treecalc/parser/antlr/simple/test/uro_endow");
		// doit("src/com/hackhofer/treecalc/parser/antlr/simple/test/nodestimes/test-nodestimes.tcs",
		// false, true,
		// "com.hackhofer.treecalc.parser.antlr.simple.test.nodestimes",
		// "c:/ws-treecalc/com.hackhofer.treecalc.model/src/com/hackhofer/treecalc/parser/antlr/simple/test/nodestimes");
		// doit("src/com/hackhofer/treecalc/parser/antlr/simple/test/nodes/test-nodes.tcs",
		// false, true, "com.hackhofer.treecalc.parser.antlr.simple.test.nodes",
		// "c:/ws-treecalc/com.hackhofer.treecalc.model/src/com/hackhofer/treecalc/parser/antlr/simple/test/nodes");
		// doit("src/com/hackhofer/treecalc/parser/antlr/simple/test/input/test-input.tcs",
		// false, true, "com.hackhofer.treecalc.parser.antlr.simple.test.input",
		// "c:/ws-treecalc/com.hackhofer.treecalc.model/src/com/hackhofer/treecalc/parser/antlr/simple/test/input");
		// doit("src/com/hackhofer/treecalc/parser/antlr/simple/test/inputmultiple/test-inputmultiple.tcs",
		// false, true,
		// "com.hackhofer.treecalc.parser.antlr.simple.test.inputmultiple",
		// "c:/ws-treecalc/com.hackhofer.treecalc.model/src/com/hackhofer/treecalc/parser/antlr/simple/test/inputmultiple");
		// doit("src/com/hackhofer/treecalc/parser/antlr/simple/test/table/test-tab.tcs",
		// false, true, "com.hackhofer.treecalc.parser.antlr.simple.test.table",
		// "c:/ws-treecalc/com.hackhofer.treecalc.model/src/com/hackhofer/treecalc/parser/antlr/simple/test/table");
		// doit("c:/ws-treecalc/GeneratedModels/abxxx.pmt.tcs", false, true,
		// "gen.abxxx", "c:/ws-treecalc/GeneratedModels/gen/abxxx");
		// doit("c:/temp/temp/cd.pmt.tcs", false, true);
		// doit("c:/temp/temp/lvu.pmt.tcs", false, true, null);
		// doit("c:/temp/temp/flv.pmt.tcs", false, true);
		// doit("c:/temp/temp/kv.pmt.tcs", false, true);
		// doit("c:/temp/temp/whb.pmt.tcs", false, true);
		// doit("c:/temp/temp/ahp.pmt.tcs", false, true);
		// doit("c:/temp/temp/buft.pmt.tcs", false, true);
		// doit("c:/ws-treecalc/GeneratedModels/awxxx.pmt.tcs", false, true,
		// "gen.awxxx", "c:/ws-treecalc/GeneratedModels/gen/awxxx");
		// doit("c:/ws-treecalc/GeneratedModels/hwh11.pmt.tcs", false, true,
		// "gen.hwh11", "c:/ws-treecalc/GeneratedModels/gen/hwh11");
		// doit("c:/ws-treecalc/GeneratedModels/hwk11.pmt.tcs", false, true,
		// "gen.hwk11", "c:/ws-treecalc/GeneratedModels/gen/hwk11");
		// doit("c:/ws-treecalc/GeneratedModels/hwu10.pmt.tcs", false, true,
		// "gen.hwu10", "c:/ws-treecalc/GeneratedModels/gen/hwu10");
		// doit("c:/temp/temp/kfz-sub-haft2011.pmt.tcs", false, true);
		// doit("c:/temp/temp/kfz-sub-kasko2011.pmt.tcs", false, true);
		// doit("c:/temp/temp/kfz-sub-unfall2010.pmt.tcs", false, true);
		// doit("c:/temp/temp/rs.pmt.tcs", false, true);
		// doit("c:/temp/temp/uv.pmt.tcs", false, true);
		// doit("c:/temp/temp/sv.pmt.tcs", false, true);
		// doit("c:/temp/temp/ukvlei.pmt.tcs", false, true);
		// doit("c:/temp/temp/ukvleitab.pmt.tcs", false, true);
		// doit("c:/temp/temp/rvwebkfz.pmt.tcs", false, true);
		// doit("c:/temp/temp/test.pmt.tcs", true, true);
		// System.out.println("total done.");
	}

	private static boolean checkFileExist(String fileWithPath) {
		File f = new File(fileWithPath);
		return f.exists();

	}

	private static void printUseUsage() {
		System.out.println("For proper use add -usage Parameter");
	}

	/**
	 * If String ends with ".tcs" return true, else false
	 * 
	 * @param s
	 * @return
	 */
	private static boolean checkTcsFileString(String s) {
		if (s.endsWith(".tcs")) {
			return true;
		} else {
			return false;
		}
	}

	private static List<String> getTestModelNames() {
		List<String> listOfModelNames = new ArrayList<String>();
		File[] listOfFiles = new File(Constants.TCS_INPUT_PATH).listFiles();
		for (File f : listOfFiles) {
			if (f.isFile() && checkTcsFileString(f.getName())) {
				listOfModelNames.add(f.getName().replace(".tcs", ""));
			}
		}
		return listOfModelNames;
	}

	private static void compileAndPacketize(String genpath, String jarName) throws IOException {

		new File(Constants.TEMP_CLASSES_FOLDER).mkdirs();
		try {
			/* compile the java files */
			runProcess("javac" + " " + "-classpath ./TreecalcGenerator.jar " + genpath + "/*.java" + " -d "
					+ Constants.TEMP_CLASSES_FOLDER);

			/* jar the classes */
			File classFiles = new File(Constants.TEMP_CLASSES_FOLDER);
			File[] files = classFiles.listFiles();
			if (jarName == null) {
				createJarArchive(new File(genpath + ".jar"), files);
			} else {
				createJarArchive(new File(jarName + ".jar"), files);
			}

			/* delete the temp folders */
			deleteDirRecursive(new File(genpath));
			deleteDirRecursive(new File(Constants.TEMP_CLASSES_FOLDER));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private static void runProcess(String command) throws IOException, InterruptedException {
		Process pro = Runtime.getRuntime().exec(command);
		printLines(command + " stdout:", pro.getInputStream());
		printLines(command + " stderr:", pro.getErrorStream());
		pro.waitFor();
		// System.out.println(command + " exitValue() " + pro.exitValue());

	}

	private static void printLines(String name, InputStream ins) throws IOException {
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		while ((line = in.readLine()) != null) {
			System.out.println(name + " " + line);
		}
	}

	private static void createJarArchive(File archiveFile, File[] tobeJared) {
		try {
			FileOutputStream stream = new FileOutputStream(archiveFile);
			JarOutputStream out = new JarOutputStream(stream, new Manifest());

			for (int i = 0; i < tobeJared.length; i++) {
				writeFile(out, tobeJared[i], "");
			}
			out.close();
			stream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error: " + ex.getMessage());
		}
	}

	private static void writeFile(JarOutputStream out, File f, String parentDirectory) throws IOException {
		byte buffer[] = new byte[Constants.BUFFER_SIZE];

		if (f == null || !f.exists()) {
			return;
		}
		JarEntry jarAdd;
		if (f.isDirectory()) {
			jarAdd = new JarEntry(parentDirectory + f.getName() + "/");
		} else {
			jarAdd = new JarEntry(parentDirectory + f.getName());
		}

		jarAdd.setTime(f.lastModified());
		out.putNextEntry(jarAdd);

		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int j = 0; j < files.length; j++) {
				writeFile(out, files[j], parentDirectory + f.getName() + "/");
			}
		} else {
			FileInputStream in = new FileInputStream(f);
			while (true) {
				int nRead = in.read(buffer, 0, buffer.length);
				if (nRead <= 0)
					break;
				out.write(buffer, 0, nRead);
			}
			in.close();
		}
	}

	public static void deleteDirRecursive(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			if (children.length != 0) {
				for (int i = 0; i < children.length; i++) {
					if (children[i].isDirectory()) {
						deleteDirRecursive(children[i]);
					} else {
						children[i].delete();
					}
				}
				dir.delete();
			} else {
				dir.delete();
			}
		}
	}

	/**
	 * Util method that returns actual maven version of the project from file
	 * pom.properties
	 * 
	 * @return
	 */
	public static String getVersionFromMaven() {
		String versionNumber = "";
		String propertiesPath = Constants.MAVEN_PATH_PRAEFIX + Constants.GROUPID + "/" + Constants.ARTIFACTID + Constants.MAVEN_PATH_POSTFIX;

		InputStream resourceStream = null;
		LineNumberReader reader = null;
		try {
			resourceStream = Action.class.getResourceAsStream(propertiesPath);
			if (resourceStream == null) {
				return "unknown";
			}
			reader = new LineNumberReader(new InputStreamReader(resourceStream));

			// skip "#Generated by Maven"
			reader.readLine();

			// skip "#timestamp"
			reader.readLine();

			// find "version=..."
			String line;
			while (null != (line = reader.readLine())) {
				String[] parts = line.split("[ \t]*=[ \t]*");
				if ("version".equals(parts[0])) {
					versionNumber = parts[1];
					break;
				}
			}
		} catch (IOException e) {
			return "unknown";
		} finally {
			try {
				/* only close the reader, the InputStream is closed also by this */
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return versionNumber;
	}
}
