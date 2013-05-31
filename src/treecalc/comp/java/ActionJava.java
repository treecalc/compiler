package treecalc.comp.java;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import treecalc.comp.FillModelStep0_Nodes;
import treecalc.comp.FillModelStep1_Symboltables;
import treecalc.comp.FillModelStep2_ResolveNames;
import treecalc.comp.GenFill;
import treecalc.comp.ModelSimple;
import treecalc.comp.TcAst;
import treecalc.comp.TcAstAdapter;
import treecalc.comp.parser.TcSimpleLexer;
import treecalc.comp.parser.TcSimpleParser;
import treecalc.comp.parser.TcSimpleParser.compilationunit_return;


public class ActionJava {

    /* TODO: generate class files directly or via java bytecode assembler for
	 * initialization parts
	 */
	private static ModelSimple model;

	private static void parseTextFile(String filename, String packagename, String genpath, boolean gwt, boolean trace) throws IOException, RecognitionException {
		ANTLRFileStream fs = new ANTLRFileStream(filename);
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

		JavaNodes.generate(model, outpath, packagename, gwt, trace);
		JavaInput.generate(model, outpath, packagename, gwt, trace);
		JavaDiv.generate(model, outpath, packagename, gwt, trace);
		JavaTables.generate(model, outpath, packagename, gwt, trace);
		JavaFunctions.generate(model, outpath, packagename, trace);
		JavaConstants.generate(model, outpath, packagename);
		HashMap<Integer, String> cacheids = model.getCacheIds();
		for (Entry<Integer,String> cacheid : cacheids.entrySet()) {
			System.out.println(cacheid.getKey() + ";" + cacheid.getValue());			
		}
	}

	public static void doit(String filenameTcs, String packagename, String genpath, boolean gwt, boolean trace) throws IOException {
		System.out.println("start parsing " + filenameTcs);
		long start = System.currentTimeMillis();
		// System.out.println(new File(".").getAbsolutePath());
		try {
			parseTextFile(filenameTcs, packagename, genpath, gwt, trace);
		} catch (RecognitionException e) {
			System.err.println("error in " + filenameTcs + ", line " + e.line + ", pos " + e.charPositionInLine);
			throw new RuntimeException(e);
		}
		long end = System.currentTimeMillis();
		long sec = (end - start) / 1000;
		System.out.println("done with " + filenameTcs + " in " + sec + " sec.");
	}

	public static void main(String[] args) throws IOException {
		doit("./src/test/resources/input/flv.tcs", "gen.flv", "./src/test/java/gen/flv", false, false);
//		doit("./src/test/resources/input/rs.tcs", "gen.rs", "./src/test/java/gen/rs", true);
//		doit("c:/vpms/tc/geb/alle_pms/GEBAEUDE.PMT.tcs", "gen.gebaeude", "./src/test/java/gen/gebaeude", true);
//		doit("./src/test/resources/input/uv.tcs", "gen.uv", "./src/test/java/gen/uv", true);
//		doit("./src/test/resources/input/testinput.tcs", "gen.testinput", "./src/test/java/gen/testinput", true);
//		doit("./src/test/resources/input/testinputmultiple.tcs", "gen.testinputmultiple", "./src/test/java/gen/testinputmultiple", true);
//		doit("./src/test/resources/input/testnodes.tcs", "gen.testnodes", "./src/test/java/gen/testnodes", true);
//		doit("./src/test/resources/input/testnodestimes.tcs", "gen.testnodestimes", "./src/test/java/gen/testnodestimes", true);
//		doit("./src/test/resources/input/testtab.tcs", "gen.testtab", "./src/test/java/gen/testtab", true);
//		doit("./src/test/resources/input/uroliendow.tcs", "gen.uroliendow", "./src/test/java/gen/uroliendow", true);
		
	}

}
