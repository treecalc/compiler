package treecalc.comp.vm;


import java.io.File;
import java.io.IOException;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import treecalc.comp.Constants;
import treecalc.comp.FillModelStep0_Nodes;
import treecalc.comp.FillModelStep1_Symboltables;
import treecalc.comp.FillModelStep2_ResolveNames;
import treecalc.comp.GenFill;
import treecalc.comp.ModelSimple;
import treecalc.comp.TcAst;
import treecalc.comp.TcAstAdapter;

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
import treecalc.vm.asm.TciLexer;
import treecalc.vm.asm.TciParser;
import treecalc.vm.asm.TciAssembler.Asm;

public class ActionVm {
	private static ModelSimple model;

	private static void parseTextFile(String filenameTcs, String genfilename,
			String genpath) throws IOException, RecognitionException {
		ANTLRFileStream fs = new ANTLRFileStream(filenameTcs);
		TcSimpleLexer lexer = new TcSimpleLexer(fs);
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		TcSimpleParser parser = new TcSimpleParser(tokens);
		parser.setTreeAdaptor(new TcAstAdapter());
		compilationunit_return x = parser.compilationunit();
		TcAst tree = (TcAst) x.getTree();
		String outpath = genpath != null && genpath.length() > 0 ? genpath + "/" : "c:/temp/temp/gen/";

		ModelSimple model = new ModelSimple(tree);

		FillModelStep0_Nodes.build(model);
		FillModelStep1_Symboltables.build(model);
		FillModelStep2_ResolveNames.build(model);

		GenFill.fillModel(model);

		String filenameTc = outpath + "/" + genfilename ;
		new File(filenameTc + ".tci").delete();
		VmNodes.generate(model, outpath, genfilename);
		VmTables.generate(model, outpath, genfilename);
		VmFunctions.generate(model, outpath, genfilename);
		VmInput.generate(model, outpath, genfilename);
		VmFormula.generate(model, outpath, genfilename);


		ANTLRFileStream fsTci = new ANTLRFileStream(filenameTc + ".tci"); 
		TciLexer lexerTci = new TciLexer(fsTci);
		CommonTokenStream tokensTci = new CommonTokenStream(lexerTci);
		TciAssembler assemblerTci = new TciAssembler();
		TciParser parserTci = new TciParser(tokensTci, assemblerTci);
		parserTci.tci();
		System.out.println(TciAction.class.getName() + ": done.");
		Asm asm = assemblerTci.getAsm();
		TciAsmReaderWriter.write(asm, filenameTc + ".tcx");
	}

	public static void doit(String filenameTcs, String genfilename, String genpath) throws IOException {
		System.out.println("start parsing " + filenameTcs);
		long start = System.currentTimeMillis();
		// System.out.println(new File(".").getAbsolutePath());
		try {
			parseTextFile(filenameTcs, genfilename, genpath);
		} catch (RecognitionException e) {
			System.err.println("error in " + filenameTcs + ", line " + e.line + ", pos " + e.charPositionInLine);
			throw new RuntimeException(e);
		}
		long end = System.currentTimeMillis();
		long sec = (end - start) / 1000;
		System.out.println("done with " + filenameTcs + " in " + sec + " sec.");
	}

	static void doittestgen(String modelname) throws IOException {
		doit(Constants.TCS_INPUT_PATH + modelname + ".tcs", "gen." + modelname.toLowerCase(),
			Constants.GENERATED_TESTSOURCE_OUTPUT_PATH + modelname.toLowerCase());
	}

	private static void usage() {
		System.err.println("arguments: filenameTcs genfilename genpath");
		System.exit(1);
	}

	public static void main(String[] args) throws IOException {
		if (args.length!=3) {
			usage();
		}
		int indarg=0;
		String filenameTcs = args[indarg++];
		String genfilename = args.length>indarg ? args[indarg++] : "";
		String genpath = args.length>indarg ? args[indarg++] : ".";
		doit(filenameTcs, genfilename, genpath);
		
//		doit("./src/test/resources/input/flv.tcs", "gen.flv", "./src/test/java/gen/flv");
//		doit("c:/vpms/tc/geb/alle_pms/GEBAEUDE.PMT.tcs", "gen.gebaeude", "./src/test/java/gen/gebaeude");
//		doit("./src/test/resources/input/uv.tcs", "gen.uv", "./src/test/java/gen/uv");
//		doit("./src/test/resources/input/testinput.tcs", "gen.testinput", "./src/test/java/gen/testinput");
//		doit("./src/test/resources/input/testinputmultiple.tcs", "gen.testinputmultiple", "./src/test/java/gen/testinputmultiple");
//		doit("./src/test/resources/input/testnodes.tcs", "gen.testnodes", "./src/test/java/gen/testnodes");
//		doit("./src/test/resources/input/testnodestimes.tcs", "gen.testnodestimes", "./src/test/java/gen/testnodestimes");
//		doit("./src/test/resources/input/testtab.tcs", "gen.testtab", "./src/test/java/gen/testtab");
//		doit("./src/test/resources/input/uroliendow.tcs", "gen.uroliendow", "./src/test/java/gen/uroliendow");
	}

}
