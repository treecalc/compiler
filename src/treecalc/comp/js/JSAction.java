package treecalc.comp.js;

import java.io.IOException;
import java.io.PrintWriter;

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
import treecalc.comp.TcSimpleLexer;
import treecalc.comp.TcSimpleParser;
import treecalc.comp.TcSimpleParser.compilationunit_return;



public class JSAction {
	/* TODO: compile on the fly without java source files */
	/* TODO: generate class files directly or via java bytecode assembler for initialization parts */
	private static ModelSimple model;
	
	
	private static void parseTextFile(Settings settings) throws IOException, RecognitionException  {
		ANTLRFileStream fs = new ANTLRFileStream(settings.getFilenamePmt()); 
        TcSimpleLexer lexer = new TcSimpleLexer(fs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
//        for(;;) {
//        	tokens.consume();
//        	int i = tokens.LA(1);
//        	if (i==CommonToken.EOF) {
//        		break;
//        	}
//        	System.out.println(i + ": " + TcSimpleParser.tokenNames[i]);
//        }

        TcSimpleParser parser = new TcSimpleParser(tokens);
        parser.setTreeAdaptor(new TcAstAdapter());
        compilationunit_return x = parser.compilationunit();
        TcAst tree = (TcAst) x.getTree();
        if (settings.isWriteast()) {
	        PrintWriter writer = new PrintWriter(settings.getFilenamePmt() + ".ast");
	        writer.append(tree.toStringTree());
	        writer.close();
        }
        if (settings.isWalkit()) {
        	String outpath = settings.getGenpath() != null && settings.getGenpath().length()>0 ? settings.getGenpath() + "/" : "c:/temp/temp/gen/";
        	
        	ModelSimple model = new ModelSimple(tree);
        	
        	FillModelStep0_Nodes.build(model);
        	FillModelStep1_Symboltables.build(model);
        	FillModelStep2_ResolveNames.build(model);
        	
        	GenFill.fillModel(model);
        	
        	JSFunctions.generate(model, outpath, settings.getPackagename());        	
        	JSDiv.generate(model, outpath, settings.getPackagename());
        	JSTables.generate(model, outpath, settings.getPackagename());
        	JSInput.generate(model, outpath, settings.getPackagename());
        	JSConstants.generate(model, outpath, settings.getPackagename());
        	
//        	FillModelStep3.generate(tree, "c:/temp/temp/gen/");
//        	model.writeToFile(new File(filename + ".tcx"));
        	
//        	PmsWalkerManual.walk(tree, filename+ ".tcs");
//        	TreeNodeStream treenodestream = new CommonTreeNodeStream(tree);
//        	PmsWalker1 walker1 = new PmsWalker1(treenodestream);
//        	walker1.compilationunit();
        	
//        	TreeNodeStream treenodestream2 = new CommonTreeNodeStream(tree);
//        	PmsTreeRewrite rewriter = new PmsTreeRewrite(treenodestream2);
//        	tree = (CommonTree) rewriter.downup(tree, true);
//        	System.out.println(tree.toStringTree());
        }
//        for(;;) {
//        	tokens.consume();
//        	int token = tokens.LA(1);
//        	if (token==Token.EOF) {
//        		break;
//        	}
//        }
	}

	public static void doit(Settings settings) throws IOException {
		System.out.println("start parsing " + settings.getFilenamePmt());
		long start = System.currentTimeMillis();
		try {
			parseTextFile(settings);
		} catch (RecognitionException e) {
			System.err.println("error in " + settings.getFilenamePmt() + ", line " + e.line + ", pos " + e.charPositionInLine);
			throw new RuntimeException(e);
		}
		long end = System.currentTimeMillis();
		long sec = (end - start) / 1000;
		System.out.println("done with " + settings.getFilenamePmt() + " in " + sec + " sec.");
	}

	/* TODO: remove autocounters that are not defined; remove autocounter-list without counters */ 
//	static void doitgen(String modelname) throws IOException {
//		String folder = "c:/ws-treecalc/GeneratedModels/";
//		doit (folder + modelname + ".pmt.tcs", 
//				false, true, 
//				"gen." + modelname.toLowerCase(), 
//				folder + "gen/" + modelname.toLowerCase()
//			);
//	}
	
	class Settings {
		private String filenamePmt;
		private boolean writeast;
		private boolean walkit;
		private String packagename;
		private String genpath;
		
		public Settings() {
			this.writeast = false;
			this.walkit = false;
		}
		
		public String getFilenamePmt() {
			return filenamePmt;
		}
		public void setFilenamePmt(String filenamePmt) {
			this.filenamePmt = filenamePmt;
		}
		public boolean isWriteast() {
			return writeast;
		}
		public void setWriteast(boolean writeast) {
			this.writeast = writeast;
		}
		public boolean isWalkit() {
			return walkit;
		}
		public void setWalkit(boolean walkit) {
			this.walkit = walkit;
		}
		public String getPackagename() {
			return packagename;
		}
		public void setPackagename(String packagename) {
			this.packagename = packagename;
		}
		public String getGenpath() {
			return genpath;
		}
		public void setGenpath(String genpath) {
			this.genpath = genpath;
		}
	};
	
	public static void main(String[] args) throws IOException {
		
		JSAction action = new JSAction();
		Settings settings = action.new Settings();
		settings.setFilenamePmt("src/test/resources/input/rs.tcs");
		settings.setWalkit(true);
		settings.setPackagename("package.ist.egal");
		settings.setGenpath("src/test/js/rs");		
		doit(settings);		
		//alles in ein js und optimieren/minimieren
		
//		JSAction action = new JSAction();
//		Settings settings = action.new Settings();
//		settings.setFilenamePmt("tcs/status/li_notree.tcs");
//		settings.setWalkit(true);
//		settings.setPackagename("package.ist.egal");
//		settings.setGenpath("js/test/status/li_notreefiles");		
//		doit(settings);		
//		
//		action = new JSAction();
//		settings = action.new Settings();
//		settings.setFilenamePmt("tcs/status/status01.tcs");
//		settings.setWalkit(true);
//		settings.setPackagename("package.ist.egal");
//		settings.setGenpath("js/test/status/test01files");		
//		doit(settings);
//		
//		action = new JSAction();
//		settings = action.new Settings();
//		settings.setFilenamePmt("tcs/table/suite.tcs");
//		settings.setWalkit(true);
//		settings.setPackagename("package.ist.egal");
//		settings.setGenpath("js/test/table/testsuitefiles");		
//		doit(settings);
//		
//		action = new JSAction();
//		settings = action.new Settings();
//		settings.setFilenamePmt("tcs/func/func03.tcs");
//		settings.setWalkit(true);
//		settings.setPackagename("package.ist.egal");
//		settings.setGenpath("js/test/func/test03files");
//		
//		doit(settings);
		/*
		for (int i=1; i<10; i++) {
			String id = "0" + i;
			action = new JSAction();
			settings = action.new Settings();
			settings.setFilenamePmt("tcs/table/table" + id + ".tcs");
			settings.setWalkit(true);
			settings.setPackagename("package.ist.egal");
			settings.setGenpath("js/test/table/test" + id + "files");
			doit(settings);
		}
		*/
//		settings.setFilenamePmt("tcs/input/input01.tcs");
//		settings.setGenpath("js/generated/input01");
//		doit(settings);
	}
}
