// $ANTLR 3.4 TcSimple.g 2011-12-06 13:21:40
package treecalc.comp; 

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class TcSimpleParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ASTERISK", "BRACKET_CLOSE", "BRACKET_OPEN", "COLON", "COMMA", "COMMENT", "COMMENTLINE", "COMMENTML", "COMPARE_BIGGER", "COMPARE_BIGGEREQUAL", "COMPARE_EQUAL", "COMPARE_EQUAL_CSTYLE", "COMPARE_LESSEQUAL", "COMPARE_NOTEQUAL", "COMPARE_NOTEQUAL_CSTYLE", "COMPARE_SMALLER", "COMPARE_STR_AFTER", "COMPARE_STR_AHEAD", "COMPARE_STR_ALIKE", "COMPARE_STR_BEFORE", "COMPARE_STR_BEHIND", "COMPARE_STR_EQUAL", "COMPARE_STR_NOTAFTER", "COMPARE_STR_NOTAHEAD", "COMPARE_STR_NOTALIKE", "COMPARE_STR_NOTBEFORE", "COMPARE_STR_NOTBEHIND", "COMPARE_STR_NOTEQUAL", "CURLY_CLOSE", "CURLY_OPEN", "DIV", "DOT", "DOTS", "DOUBLEASTERISK", "EXPONENT", "EscapeSequence", "ID", "KEYWORD_AS", "KEYWORD_CALC", "KEYWORD_CASE", "KEYWORD_CELL", "KEYWORD_CELLX", "KEYWORD_COLLATE", "KEYWORD_COUNTERLIST", "KEYWORD_DEFAULT", "KEYWORD_DOCALL", "KEYWORD_ELSE", "KEYWORD_ENDCASE", "KEYWORD_ENDIF", "KEYWORD_EXISTS", "KEYWORD_EXTRACT", "KEYWORD_FUNC", "KEYWORD_FUNCREF", "KEYWORD_IF", "KEYWORD_INPUT", "KEYWORD_INTERPOL", "KEYWORD_LINK", "KEYWORD_LOOKDOWNX", "KEYWORD_LOOKUP", "KEYWORD_LOOKUPX", "KEYWORD_NODE", "KEYWORD_PRODX", "KEYWORD_SUMX", "KEYWORD_TABCOLS", "KEYWORD_TABLE", "KEYWORD_TABREF", "KEYWORD_TABROWS", "KEYWORD_THEN", "KEYWORD_TIMES", "KEYWORD_TREE", "KEYWORD_VECTORX", "KEYWORD_WHEN", "LOGICAL_AND", "LOGICAL_OR", "LOGICAL_XOR", "MINUS", "MOD", "NUMBER", "NUMBER_INT", "PAREN_CLOSE", "PAREN_OPEN", "PLUS", "POWER", "QUESTIONMARK", "SEMICOLON", "SLASH", "STRCAT", "STRING", "TT_ARGDEF", "TT_BUILTIN", "TT_CALCCALL", "TT_CASECOMPARISON", "TT_CASECONDITION", "TT_CASERANGE", "TT_COLNAMEFORMULA", "TT_COLNAMESTATIC", "TT_COMPUNIT", "TT_DYNTABLE", "TT_FUNCALL", "TT_FUNORCALCCALL", "TT_IDLIST", "TT_INCLUSIONFORMULA", "TT_INDEX", "TT_INPUTACCESS", "TT_INPUTACCESSRAWSELF", "TT_INPUTACCESSSIMPLE", "TT_INPUTCALCCALL", "TT_INPUTCALCCALLSIMPLE", "TT_INPUTORTABACCESSWITHINDEX", "TT_NODEPATH", "TT_PARAMETERS", "TT_RANGE", "TT_RESULTDEF", "TT_TABLEACCESS", "TT_TABLELINE", "TT_TABREF", "TT_TIMESINFO", "TT_USEID", "TT_USEVAR_LOCAL", "TT_USEVAR_PARAMETER", "TT_USEVAR_TIMESINDEX", "WHITESPACE"
    };

    public static final int EOF=-1;
    public static final int ASTERISK=4;
    public static final int BRACKET_CLOSE=5;
    public static final int BRACKET_OPEN=6;
    public static final int COLON=7;
    public static final int COMMA=8;
    public static final int COMMENT=9;
    public static final int COMMENTLINE=10;
    public static final int COMMENTML=11;
    public static final int COMPARE_BIGGER=12;
    public static final int COMPARE_BIGGEREQUAL=13;
    public static final int COMPARE_EQUAL=14;
    public static final int COMPARE_EQUAL_CSTYLE=15;
    public static final int COMPARE_LESSEQUAL=16;
    public static final int COMPARE_NOTEQUAL=17;
    public static final int COMPARE_NOTEQUAL_CSTYLE=18;
    public static final int COMPARE_SMALLER=19;
    public static final int COMPARE_STR_AFTER=20;
    public static final int COMPARE_STR_AHEAD=21;
    public static final int COMPARE_STR_ALIKE=22;
    public static final int COMPARE_STR_BEFORE=23;
    public static final int COMPARE_STR_BEHIND=24;
    public static final int COMPARE_STR_EQUAL=25;
    public static final int COMPARE_STR_NOTAFTER=26;
    public static final int COMPARE_STR_NOTAHEAD=27;
    public static final int COMPARE_STR_NOTALIKE=28;
    public static final int COMPARE_STR_NOTBEFORE=29;
    public static final int COMPARE_STR_NOTBEHIND=30;
    public static final int COMPARE_STR_NOTEQUAL=31;
    public static final int CURLY_CLOSE=32;
    public static final int CURLY_OPEN=33;
    public static final int DIV=34;
    public static final int DOT=35;
    public static final int DOTS=36;
    public static final int DOUBLEASTERISK=37;
    public static final int EXPONENT=38;
    public static final int EscapeSequence=39;
    public static final int ID=40;
    public static final int KEYWORD_AS=41;
    public static final int KEYWORD_CALC=42;
    public static final int KEYWORD_CASE=43;
    public static final int KEYWORD_CELL=44;
    public static final int KEYWORD_CELLX=45;
    public static final int KEYWORD_COLLATE=46;
    public static final int KEYWORD_COUNTERLIST=47;
    public static final int KEYWORD_DEFAULT=48;
    public static final int KEYWORD_DOCALL=49;
    public static final int KEYWORD_ELSE=50;
    public static final int KEYWORD_ENDCASE=51;
    public static final int KEYWORD_ENDIF=52;
    public static final int KEYWORD_EXISTS=53;
    public static final int KEYWORD_EXTRACT=54;
    public static final int KEYWORD_FUNC=55;
    public static final int KEYWORD_FUNCREF=56;
    public static final int KEYWORD_IF=57;
    public static final int KEYWORD_INPUT=58;
    public static final int KEYWORD_INTERPOL=59;
    public static final int KEYWORD_LINK=60;
    public static final int KEYWORD_LOOKDOWNX=61;
    public static final int KEYWORD_LOOKUP=62;
    public static final int KEYWORD_LOOKUPX=63;
    public static final int KEYWORD_NODE=64;
    public static final int KEYWORD_PRODX=65;
    public static final int KEYWORD_SUMX=66;
    public static final int KEYWORD_TABCOLS=67;
    public static final int KEYWORD_TABLE=68;
    public static final int KEYWORD_TABREF=69;
    public static final int KEYWORD_TABROWS=70;
    public static final int KEYWORD_THEN=71;
    public static final int KEYWORD_TIMES=72;
    public static final int KEYWORD_TREE=73;
    public static final int KEYWORD_VECTORX=74;
    public static final int KEYWORD_WHEN=75;
    public static final int LOGICAL_AND=76;
    public static final int LOGICAL_OR=77;
    public static final int LOGICAL_XOR=78;
    public static final int MINUS=79;
    public static final int MOD=80;
    public static final int NUMBER=81;
    public static final int NUMBER_INT=82;
    public static final int PAREN_CLOSE=83;
    public static final int PAREN_OPEN=84;
    public static final int PLUS=85;
    public static final int POWER=86;
    public static final int QUESTIONMARK=87;
    public static final int SEMICOLON=88;
    public static final int SLASH=89;
    public static final int STRCAT=90;
    public static final int STRING=91;
    public static final int TT_ARGDEF=92;
    public static final int TT_BUILTIN=93;
    public static final int TT_CALCCALL=94;
    public static final int TT_CASECOMPARISON=95;
    public static final int TT_CASECONDITION=96;
    public static final int TT_CASERANGE=97;
    public static final int TT_COLNAMEFORMULA=98;
    public static final int TT_COLNAMESTATIC=99;
    public static final int TT_COMPUNIT=100;
    public static final int TT_DYNTABLE=101;
    public static final int TT_FUNCALL=102;
    public static final int TT_FUNORCALCCALL=103;
    public static final int TT_IDLIST=104;
    public static final int TT_INCLUSIONFORMULA=105;
    public static final int TT_INDEX=106;
    public static final int TT_INPUTACCESS=107;
    public static final int TT_INPUTACCESSRAWSELF=108;
    public static final int TT_INPUTACCESSSIMPLE=109;
    public static final int TT_INPUTCALCCALL=110;
    public static final int TT_INPUTCALCCALLSIMPLE=111;
    public static final int TT_INPUTORTABACCESSWITHINDEX=112;
    public static final int TT_NODEPATH=113;
    public static final int TT_PARAMETERS=114;
    public static final int TT_RANGE=115;
    public static final int TT_RESULTDEF=116;
    public static final int TT_TABLEACCESS=117;
    public static final int TT_TABLELINE=118;
    public static final int TT_TABREF=119;
    public static final int TT_TIMESINFO=120;
    public static final int TT_USEID=121;
    public static final int TT_USEVAR_LOCAL=122;
    public static final int TT_USEVAR_PARAMETER=123;
    public static final int TT_USEVAR_TIMESINDEX=124;
    public static final int WHITESPACE=125;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public TcSimpleParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public TcSimpleParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return TcSimpleParser.tokenNames; }
    public String getGrammarFileName() { return "TcSimple.g"; }


    public static class compilationunit_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "compilationunit"
    // TcSimple.g:149:1: compilationunit : ( def )+ -> ^( TT_COMPUNIT ( def )* ) ;
    public final TcSimpleParser.compilationunit_return compilationunit() throws RecognitionException {
        TcSimpleParser.compilationunit_return retval = new TcSimpleParser.compilationunit_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        TcSimpleParser.def_return def1 =null;


        RewriteRuleSubtreeStream stream_def=new RewriteRuleSubtreeStream(adaptor,"rule def");
        try {
            // TcSimple.g:149:16: ( ( def )+ -> ^( TT_COMPUNIT ( def )* ) )
            // TcSimple.g:149:18: ( def )+
            {
            // TcSimple.g:149:18: ( def )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==KEYWORD_CALC||LA1_0==KEYWORD_FUNC||LA1_0==KEYWORD_INPUT||LA1_0==KEYWORD_TABLE||LA1_0==KEYWORD_TREE) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // TcSimple.g:149:18: def
            	    {
            	    pushFollow(FOLLOW_def_in_compilationunit1291);
            	    def1=def();

            	    state._fsp--;

            	    stream_def.add(def1.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            // AST REWRITE
            // elements: def
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 149:23: -> ^( TT_COMPUNIT ( def )* )
            {
                // TcSimple.g:149:26: ^( TT_COMPUNIT ( def )* )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                (TcAst)adaptor.create(TT_COMPUNIT, "TT_COMPUNIT")
                , root_1);

                // TcSimple.g:149:40: ( def )*
                while ( stream_def.hasNext() ) {
                    adaptor.addChild(root_1, stream_def.nextTree());

                }
                stream_def.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "compilationunit"


    public static class def_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "def"
    // TcSimple.g:150:1: def : ( KEYWORD_TREE nodepath CURLY_OPEN ( nodeinfo )* CURLY_CLOSE -> ^( KEYWORD_TREE nodepath ( nodeinfo )* ) | KEYWORD_CALC nodepath CURLY_OPEN ( resultdef )* CURLY_CLOSE -> ^( KEYWORD_CALC nodepath ( resultdef )* ) | KEYWORD_INPUT id ( ( CURLY_OPEN ( resultdef )* CURLY_CLOSE ) | SEMICOLON ) -> ^( KEYWORD_INPUT id ( resultdef )* ) | KEYWORD_FUNC resultdef -> ^( KEYWORD_FUNC resultdef ) | KEYWORD_TABLE id PAREN_OPEN colnames PAREN_CLOSE CURLY_OPEN ( tableline )* CURLY_CLOSE -> ^( KEYWORD_TABLE id colnames ( tableline )* ) );
    public final TcSimpleParser.def_return def() throws RecognitionException {
        TcSimpleParser.def_return retval = new TcSimpleParser.def_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token KEYWORD_TREE2=null;
        Token CURLY_OPEN4=null;
        Token CURLY_CLOSE6=null;
        Token KEYWORD_CALC7=null;
        Token CURLY_OPEN9=null;
        Token CURLY_CLOSE11=null;
        Token KEYWORD_INPUT12=null;
        Token CURLY_OPEN14=null;
        Token CURLY_CLOSE16=null;
        Token SEMICOLON17=null;
        Token KEYWORD_FUNC18=null;
        Token KEYWORD_TABLE20=null;
        Token PAREN_OPEN22=null;
        Token PAREN_CLOSE24=null;
        Token CURLY_OPEN25=null;
        Token CURLY_CLOSE27=null;
        TcSimpleParser.nodepath_return nodepath3 =null;

        TcSimpleParser.nodeinfo_return nodeinfo5 =null;

        TcSimpleParser.nodepath_return nodepath8 =null;

        TcSimpleParser.resultdef_return resultdef10 =null;

        TcSimpleParser.id_return id13 =null;

        TcSimpleParser.resultdef_return resultdef15 =null;

        TcSimpleParser.resultdef_return resultdef19 =null;

        TcSimpleParser.id_return id21 =null;

        TcSimpleParser.colnames_return colnames23 =null;

        TcSimpleParser.tableline_return tableline26 =null;


        TcAst KEYWORD_TREE2_tree=null;
        TcAst CURLY_OPEN4_tree=null;
        TcAst CURLY_CLOSE6_tree=null;
        TcAst KEYWORD_CALC7_tree=null;
        TcAst CURLY_OPEN9_tree=null;
        TcAst CURLY_CLOSE11_tree=null;
        TcAst KEYWORD_INPUT12_tree=null;
        TcAst CURLY_OPEN14_tree=null;
        TcAst CURLY_CLOSE16_tree=null;
        TcAst SEMICOLON17_tree=null;
        TcAst KEYWORD_FUNC18_tree=null;
        TcAst KEYWORD_TABLE20_tree=null;
        TcAst PAREN_OPEN22_tree=null;
        TcAst PAREN_CLOSE24_tree=null;
        TcAst CURLY_OPEN25_tree=null;
        TcAst CURLY_CLOSE27_tree=null;
        RewriteRuleTokenStream stream_KEYWORD_INPUT=new RewriteRuleTokenStream(adaptor,"token KEYWORD_INPUT");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_KEYWORD_CALC=new RewriteRuleTokenStream(adaptor,"token KEYWORD_CALC");
        RewriteRuleTokenStream stream_PAREN_OPEN=new RewriteRuleTokenStream(adaptor,"token PAREN_OPEN");
        RewriteRuleTokenStream stream_CURLY_OPEN=new RewriteRuleTokenStream(adaptor,"token CURLY_OPEN");
        RewriteRuleTokenStream stream_PAREN_CLOSE=new RewriteRuleTokenStream(adaptor,"token PAREN_CLOSE");
        RewriteRuleTokenStream stream_KEYWORD_TABLE=new RewriteRuleTokenStream(adaptor,"token KEYWORD_TABLE");
        RewriteRuleTokenStream stream_KEYWORD_TREE=new RewriteRuleTokenStream(adaptor,"token KEYWORD_TREE");
        RewriteRuleTokenStream stream_KEYWORD_FUNC=new RewriteRuleTokenStream(adaptor,"token KEYWORD_FUNC");
        RewriteRuleTokenStream stream_CURLY_CLOSE=new RewriteRuleTokenStream(adaptor,"token CURLY_CLOSE");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_nodepath=new RewriteRuleSubtreeStream(adaptor,"rule nodepath");
        RewriteRuleSubtreeStream stream_tableline=new RewriteRuleSubtreeStream(adaptor,"rule tableline");
        RewriteRuleSubtreeStream stream_resultdef=new RewriteRuleSubtreeStream(adaptor,"rule resultdef");
        RewriteRuleSubtreeStream stream_nodeinfo=new RewriteRuleSubtreeStream(adaptor,"rule nodeinfo");
        RewriteRuleSubtreeStream stream_colnames=new RewriteRuleSubtreeStream(adaptor,"rule colnames");
        try {
            // TcSimple.g:150:4: ( KEYWORD_TREE nodepath CURLY_OPEN ( nodeinfo )* CURLY_CLOSE -> ^( KEYWORD_TREE nodepath ( nodeinfo )* ) | KEYWORD_CALC nodepath CURLY_OPEN ( resultdef )* CURLY_CLOSE -> ^( KEYWORD_CALC nodepath ( resultdef )* ) | KEYWORD_INPUT id ( ( CURLY_OPEN ( resultdef )* CURLY_CLOSE ) | SEMICOLON ) -> ^( KEYWORD_INPUT id ( resultdef )* ) | KEYWORD_FUNC resultdef -> ^( KEYWORD_FUNC resultdef ) | KEYWORD_TABLE id PAREN_OPEN colnames PAREN_CLOSE CURLY_OPEN ( tableline )* CURLY_CLOSE -> ^( KEYWORD_TABLE id colnames ( tableline )* ) )
            int alt7=5;
            switch ( input.LA(1) ) {
            case KEYWORD_TREE:
                {
                alt7=1;
                }
                break;
            case KEYWORD_CALC:
                {
                alt7=2;
                }
                break;
            case KEYWORD_INPUT:
                {
                alt7=3;
                }
                break;
            case KEYWORD_FUNC:
                {
                alt7=4;
                }
                break;
            case KEYWORD_TABLE:
                {
                alt7=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;

            }

            switch (alt7) {
                case 1 :
                    // TcSimple.g:151:4: KEYWORD_TREE nodepath CURLY_OPEN ( nodeinfo )* CURLY_CLOSE
                    {
                    KEYWORD_TREE2=(Token)match(input,KEYWORD_TREE,FOLLOW_KEYWORD_TREE_in_def1311);  
                    stream_KEYWORD_TREE.add(KEYWORD_TREE2);


                    pushFollow(FOLLOW_nodepath_in_def1313);
                    nodepath3=nodepath();

                    state._fsp--;

                    stream_nodepath.add(nodepath3.getTree());

                    CURLY_OPEN4=(Token)match(input,CURLY_OPEN,FOLLOW_CURLY_OPEN_in_def1315);  
                    stream_CURLY_OPEN.add(CURLY_OPEN4);


                    // TcSimple.g:151:37: ( nodeinfo )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==KEYWORD_LINK||LA2_0==KEYWORD_NODE) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // TcSimple.g:151:37: nodeinfo
                    	    {
                    	    pushFollow(FOLLOW_nodeinfo_in_def1317);
                    	    nodeinfo5=nodeinfo();

                    	    state._fsp--;

                    	    stream_nodeinfo.add(nodeinfo5.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);


                    CURLY_CLOSE6=(Token)match(input,CURLY_CLOSE,FOLLOW_CURLY_CLOSE_in_def1320);  
                    stream_CURLY_CLOSE.add(CURLY_CLOSE6);


                    // AST REWRITE
                    // elements: nodeinfo, KEYWORD_TREE, nodepath
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 151:73: -> ^( KEYWORD_TREE nodepath ( nodeinfo )* )
                    {
                        // TcSimple.g:151:76: ^( KEYWORD_TREE nodepath ( nodeinfo )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_TREE.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_nodepath.nextTree());

                        // TcSimple.g:151:100: ( nodeinfo )*
                        while ( stream_nodeinfo.hasNext() ) {
                            adaptor.addChild(root_1, stream_nodeinfo.nextTree());

                        }
                        stream_nodeinfo.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // TcSimple.g:152:4: KEYWORD_CALC nodepath CURLY_OPEN ( resultdef )* CURLY_CLOSE
                    {
                    KEYWORD_CALC7=(Token)match(input,KEYWORD_CALC,FOLLOW_KEYWORD_CALC_in_def1350);  
                    stream_KEYWORD_CALC.add(KEYWORD_CALC7);


                    pushFollow(FOLLOW_nodepath_in_def1352);
                    nodepath8=nodepath();

                    state._fsp--;

                    stream_nodepath.add(nodepath8.getTree());

                    CURLY_OPEN9=(Token)match(input,CURLY_OPEN,FOLLOW_CURLY_OPEN_in_def1354);  
                    stream_CURLY_OPEN.add(CURLY_OPEN9);


                    // TcSimple.g:152:37: ( resultdef )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0 >= ID && LA3_0 <= KEYWORD_INTERPOL)||(LA3_0 >= KEYWORD_LOOKDOWNX && LA3_0 <= KEYWORD_TABLE)||(LA3_0 >= KEYWORD_TABROWS && LA3_0 <= KEYWORD_THEN)||(LA3_0 >= KEYWORD_TREE && LA3_0 <= KEYWORD_WHEN)) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // TcSimple.g:152:37: resultdef
                    	    {
                    	    pushFollow(FOLLOW_resultdef_in_def1356);
                    	    resultdef10=resultdef();

                    	    state._fsp--;

                    	    stream_resultdef.add(resultdef10.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);


                    CURLY_CLOSE11=(Token)match(input,CURLY_CLOSE,FOLLOW_CURLY_CLOSE_in_def1359);  
                    stream_CURLY_CLOSE.add(CURLY_CLOSE11);


                    // AST REWRITE
                    // elements: nodepath, resultdef, KEYWORD_CALC
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 152:73: -> ^( KEYWORD_CALC nodepath ( resultdef )* )
                    {
                        // TcSimple.g:152:76: ^( KEYWORD_CALC nodepath ( resultdef )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_CALC.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_nodepath.nextTree());

                        // TcSimple.g:152:101: ( resultdef )*
                        while ( stream_resultdef.hasNext() ) {
                            adaptor.addChild(root_1, stream_resultdef.nextTree());

                        }
                        stream_resultdef.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 3 :
                    // TcSimple.g:153:4: KEYWORD_INPUT id ( ( CURLY_OPEN ( resultdef )* CURLY_CLOSE ) | SEMICOLON )
                    {
                    KEYWORD_INPUT12=(Token)match(input,KEYWORD_INPUT,FOLLOW_KEYWORD_INPUT_in_def1389);  
                    stream_KEYWORD_INPUT.add(KEYWORD_INPUT12);


                    pushFollow(FOLLOW_id_in_def1391);
                    id13=id();

                    state._fsp--;

                    stream_id.add(id13.getTree());

                    // TcSimple.g:153:21: ( ( CURLY_OPEN ( resultdef )* CURLY_CLOSE ) | SEMICOLON )
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0==CURLY_OPEN) ) {
                        alt5=1;
                    }
                    else if ( (LA5_0==SEMICOLON) ) {
                        alt5=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 5, 0, input);

                        throw nvae;

                    }
                    switch (alt5) {
                        case 1 :
                            // TcSimple.g:153:22: ( CURLY_OPEN ( resultdef )* CURLY_CLOSE )
                            {
                            // TcSimple.g:153:22: ( CURLY_OPEN ( resultdef )* CURLY_CLOSE )
                            // TcSimple.g:153:23: CURLY_OPEN ( resultdef )* CURLY_CLOSE
                            {
                            CURLY_OPEN14=(Token)match(input,CURLY_OPEN,FOLLOW_CURLY_OPEN_in_def1395);  
                            stream_CURLY_OPEN.add(CURLY_OPEN14);


                            // TcSimple.g:153:34: ( resultdef )*
                            loop4:
                            do {
                                int alt4=2;
                                int LA4_0 = input.LA(1);

                                if ( ((LA4_0 >= ID && LA4_0 <= KEYWORD_INTERPOL)||(LA4_0 >= KEYWORD_LOOKDOWNX && LA4_0 <= KEYWORD_TABLE)||(LA4_0 >= KEYWORD_TABROWS && LA4_0 <= KEYWORD_THEN)||(LA4_0 >= KEYWORD_TREE && LA4_0 <= KEYWORD_WHEN)) ) {
                                    alt4=1;
                                }


                                switch (alt4) {
                            	case 1 :
                            	    // TcSimple.g:153:34: resultdef
                            	    {
                            	    pushFollow(FOLLOW_resultdef_in_def1397);
                            	    resultdef15=resultdef();

                            	    state._fsp--;

                            	    stream_resultdef.add(resultdef15.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop4;
                                }
                            } while (true);


                            CURLY_CLOSE16=(Token)match(input,CURLY_CLOSE,FOLLOW_CURLY_CLOSE_in_def1400);  
                            stream_CURLY_CLOSE.add(CURLY_CLOSE16);


                            }


                            }
                            break;
                        case 2 :
                            // TcSimple.g:153:60: SEMICOLON
                            {
                            SEMICOLON17=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_def1405);  
                            stream_SEMICOLON.add(SEMICOLON17);


                            }
                            break;

                    }


                    // AST REWRITE
                    // elements: resultdef, id, KEYWORD_INPUT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 153:73: -> ^( KEYWORD_INPUT id ( resultdef )* )
                    {
                        // TcSimple.g:153:76: ^( KEYWORD_INPUT id ( resultdef )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_INPUT.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_id.nextTree());

                        // TcSimple.g:153:95: ( resultdef )*
                        while ( stream_resultdef.hasNext() ) {
                            adaptor.addChild(root_1, stream_resultdef.nextTree());

                        }
                        stream_resultdef.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 4 :
                    // TcSimple.g:154:4: KEYWORD_FUNC resultdef
                    {
                    KEYWORD_FUNC18=(Token)match(input,KEYWORD_FUNC,FOLLOW_KEYWORD_FUNC_in_def1424);  
                    stream_KEYWORD_FUNC.add(KEYWORD_FUNC18);


                    pushFollow(FOLLOW_resultdef_in_def1426);
                    resultdef19=resultdef();

                    state._fsp--;

                    stream_resultdef.add(resultdef19.getTree());

                    // AST REWRITE
                    // elements: resultdef, KEYWORD_FUNC
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 154:73: -> ^( KEYWORD_FUNC resultdef )
                    {
                        // TcSimple.g:154:76: ^( KEYWORD_FUNC resultdef )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_FUNC.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_resultdef.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 5 :
                    // TcSimple.g:155:4: KEYWORD_TABLE id PAREN_OPEN colnames PAREN_CLOSE CURLY_OPEN ( tableline )* CURLY_CLOSE
                    {
                    KEYWORD_TABLE20=(Token)match(input,KEYWORD_TABLE,FOLLOW_KEYWORD_TABLE_in_def1485);  
                    stream_KEYWORD_TABLE.add(KEYWORD_TABLE20);


                    pushFollow(FOLLOW_id_in_def1487);
                    id21=id();

                    state._fsp--;

                    stream_id.add(id21.getTree());

                    PAREN_OPEN22=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_def1489);  
                    stream_PAREN_OPEN.add(PAREN_OPEN22);


                    pushFollow(FOLLOW_colnames_in_def1491);
                    colnames23=colnames();

                    state._fsp--;

                    stream_colnames.add(colnames23.getTree());

                    PAREN_CLOSE24=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_def1493);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE24);


                    CURLY_OPEN25=(Token)match(input,CURLY_OPEN,FOLLOW_CURLY_OPEN_in_def1496);  
                    stream_CURLY_OPEN.add(CURLY_OPEN25);


                    // TcSimple.g:155:65: ( tableline )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0 >= ID && LA6_0 <= KEYWORD_INTERPOL)||(LA6_0 >= KEYWORD_LOOKDOWNX && LA6_0 <= KEYWORD_TABLE)||(LA6_0 >= KEYWORD_TABROWS && LA6_0 <= KEYWORD_THEN)||(LA6_0 >= KEYWORD_TREE && LA6_0 <= KEYWORD_WHEN)||LA6_0==NUMBER||LA6_0==STRING) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // TcSimple.g:155:65: tableline
                    	    {
                    	    pushFollow(FOLLOW_tableline_in_def1498);
                    	    tableline26=tableline();

                    	    state._fsp--;

                    	    stream_tableline.add(tableline26.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);


                    CURLY_CLOSE27=(Token)match(input,CURLY_CLOSE,FOLLOW_CURLY_CLOSE_in_def1501);  
                    stream_CURLY_CLOSE.add(CURLY_CLOSE27);


                    // AST REWRITE
                    // elements: colnames, KEYWORD_TABLE, tableline, id
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 156:73: -> ^( KEYWORD_TABLE id colnames ( tableline )* )
                    {
                        // TcSimple.g:156:76: ^( KEYWORD_TABLE id colnames ( tableline )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_TABLE.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_id.nextTree());

                        adaptor.addChild(root_1, stream_colnames.nextTree());

                        // TcSimple.g:156:104: ( tableline )*
                        while ( stream_tableline.hasNext() ) {
                            adaptor.addChild(root_1, stream_tableline.nextTree());

                        }
                        stream_tableline.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "def"


    public static class nodeinfo_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "nodeinfo"
    // TcSimple.g:159:1: nodeinfo : ( KEYWORD_NODE nodename ( KEYWORD_AS id )? ( nodeinclusion )? ( nodetimes )? ( SEMICOLON | ( CURLY_OPEN ( nodeinfo )* CURLY_CLOSE ) ) -> ^( KEYWORD_NODE nodename ( ^( KEYWORD_AS id ) )? ( nodeinclusion )? ( nodetimes )? ( nodeinfo )* ) | KEYWORD_LINK linkpath SEMICOLON -> ^( KEYWORD_LINK linkpath ) );
    public final TcSimpleParser.nodeinfo_return nodeinfo() throws RecognitionException {
        TcSimpleParser.nodeinfo_return retval = new TcSimpleParser.nodeinfo_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token KEYWORD_NODE28=null;
        Token KEYWORD_AS30=null;
        Token SEMICOLON34=null;
        Token CURLY_OPEN35=null;
        Token CURLY_CLOSE37=null;
        Token KEYWORD_LINK38=null;
        Token SEMICOLON40=null;
        TcSimpleParser.nodename_return nodename29 =null;

        TcSimpleParser.id_return id31 =null;

        TcSimpleParser.nodeinclusion_return nodeinclusion32 =null;

        TcSimpleParser.nodetimes_return nodetimes33 =null;

        TcSimpleParser.nodeinfo_return nodeinfo36 =null;

        TcSimpleParser.linkpath_return linkpath39 =null;


        TcAst KEYWORD_NODE28_tree=null;
        TcAst KEYWORD_AS30_tree=null;
        TcAst SEMICOLON34_tree=null;
        TcAst CURLY_OPEN35_tree=null;
        TcAst CURLY_CLOSE37_tree=null;
        TcAst KEYWORD_LINK38_tree=null;
        TcAst SEMICOLON40_tree=null;
        RewriteRuleTokenStream stream_KEYWORD_AS=new RewriteRuleTokenStream(adaptor,"token KEYWORD_AS");
        RewriteRuleTokenStream stream_KEYWORD_LINK=new RewriteRuleTokenStream(adaptor,"token KEYWORD_LINK");
        RewriteRuleTokenStream stream_KEYWORD_NODE=new RewriteRuleTokenStream(adaptor,"token KEYWORD_NODE");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_CURLY_OPEN=new RewriteRuleTokenStream(adaptor,"token CURLY_OPEN");
        RewriteRuleTokenStream stream_CURLY_CLOSE=new RewriteRuleTokenStream(adaptor,"token CURLY_CLOSE");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_nodeinclusion=new RewriteRuleSubtreeStream(adaptor,"rule nodeinclusion");
        RewriteRuleSubtreeStream stream_linkpath=new RewriteRuleSubtreeStream(adaptor,"rule linkpath");
        RewriteRuleSubtreeStream stream_nodename=new RewriteRuleSubtreeStream(adaptor,"rule nodename");
        RewriteRuleSubtreeStream stream_nodeinfo=new RewriteRuleSubtreeStream(adaptor,"rule nodeinfo");
        RewriteRuleSubtreeStream stream_nodetimes=new RewriteRuleSubtreeStream(adaptor,"rule nodetimes");
        try {
            // TcSimple.g:159:9: ( KEYWORD_NODE nodename ( KEYWORD_AS id )? ( nodeinclusion )? ( nodetimes )? ( SEMICOLON | ( CURLY_OPEN ( nodeinfo )* CURLY_CLOSE ) ) -> ^( KEYWORD_NODE nodename ( ^( KEYWORD_AS id ) )? ( nodeinclusion )? ( nodetimes )? ( nodeinfo )* ) | KEYWORD_LINK linkpath SEMICOLON -> ^( KEYWORD_LINK linkpath ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==KEYWORD_NODE) ) {
                alt13=1;
            }
            else if ( (LA13_0==KEYWORD_LINK) ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;

            }
            switch (alt13) {
                case 1 :
                    // TcSimple.g:160:4: KEYWORD_NODE nodename ( KEYWORD_AS id )? ( nodeinclusion )? ( nodetimes )? ( SEMICOLON | ( CURLY_OPEN ( nodeinfo )* CURLY_CLOSE ) )
                    {
                    KEYWORD_NODE28=(Token)match(input,KEYWORD_NODE,FOLLOW_KEYWORD_NODE_in_nodeinfo1638);  
                    stream_KEYWORD_NODE.add(KEYWORD_NODE28);


                    pushFollow(FOLLOW_nodename_in_nodeinfo1640);
                    nodename29=nodename();

                    state._fsp--;

                    stream_nodename.add(nodename29.getTree());

                    // TcSimple.g:160:26: ( KEYWORD_AS id )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==KEYWORD_AS) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // TcSimple.g:160:27: KEYWORD_AS id
                            {
                            KEYWORD_AS30=(Token)match(input,KEYWORD_AS,FOLLOW_KEYWORD_AS_in_nodeinfo1643);  
                            stream_KEYWORD_AS.add(KEYWORD_AS30);


                            pushFollow(FOLLOW_id_in_nodeinfo1645);
                            id31=id();

                            state._fsp--;

                            stream_id.add(id31.getTree());

                            }
                            break;

                    }


                    // TcSimple.g:160:43: ( nodeinclusion )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==KEYWORD_IF) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // TcSimple.g:160:43: nodeinclusion
                            {
                            pushFollow(FOLLOW_nodeinclusion_in_nodeinfo1649);
                            nodeinclusion32=nodeinclusion();

                            state._fsp--;

                            stream_nodeinclusion.add(nodeinclusion32.getTree());

                            }
                            break;

                    }


                    // TcSimple.g:160:58: ( nodetimes )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==KEYWORD_TIMES) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // TcSimple.g:160:58: nodetimes
                            {
                            pushFollow(FOLLOW_nodetimes_in_nodeinfo1652);
                            nodetimes33=nodetimes();

                            state._fsp--;

                            stream_nodetimes.add(nodetimes33.getTree());

                            }
                            break;

                    }


                    // TcSimple.g:160:69: ( SEMICOLON | ( CURLY_OPEN ( nodeinfo )* CURLY_CLOSE ) )
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==SEMICOLON) ) {
                        alt12=1;
                    }
                    else if ( (LA12_0==CURLY_OPEN) ) {
                        alt12=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 0, input);

                        throw nvae;

                    }
                    switch (alt12) {
                        case 1 :
                            // TcSimple.g:160:70: SEMICOLON
                            {
                            SEMICOLON34=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_nodeinfo1656);  
                            stream_SEMICOLON.add(SEMICOLON34);


                            }
                            break;
                        case 2 :
                            // TcSimple.g:160:82: ( CURLY_OPEN ( nodeinfo )* CURLY_CLOSE )
                            {
                            // TcSimple.g:160:82: ( CURLY_OPEN ( nodeinfo )* CURLY_CLOSE )
                            // TcSimple.g:160:83: CURLY_OPEN ( nodeinfo )* CURLY_CLOSE
                            {
                            CURLY_OPEN35=(Token)match(input,CURLY_OPEN,FOLLOW_CURLY_OPEN_in_nodeinfo1661);  
                            stream_CURLY_OPEN.add(CURLY_OPEN35);


                            // TcSimple.g:160:94: ( nodeinfo )*
                            loop11:
                            do {
                                int alt11=2;
                                int LA11_0 = input.LA(1);

                                if ( (LA11_0==KEYWORD_LINK||LA11_0==KEYWORD_NODE) ) {
                                    alt11=1;
                                }


                                switch (alt11) {
                            	case 1 :
                            	    // TcSimple.g:160:94: nodeinfo
                            	    {
                            	    pushFollow(FOLLOW_nodeinfo_in_nodeinfo1663);
                            	    nodeinfo36=nodeinfo();

                            	    state._fsp--;

                            	    stream_nodeinfo.add(nodeinfo36.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop11;
                                }
                            } while (true);


                            CURLY_CLOSE37=(Token)match(input,CURLY_CLOSE,FOLLOW_CURLY_CLOSE_in_nodeinfo1666);  
                            stream_CURLY_CLOSE.add(CURLY_CLOSE37);


                            }


                            }
                            break;

                    }


                    // AST REWRITE
                    // elements: nodetimes, KEYWORD_NODE, id, nodename, KEYWORD_AS, nodeinfo, nodeinclusion
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 161:6: -> ^( KEYWORD_NODE nodename ( ^( KEYWORD_AS id ) )? ( nodeinclusion )? ( nodetimes )? ( nodeinfo )* )
                    {
                        // TcSimple.g:161:9: ^( KEYWORD_NODE nodename ( ^( KEYWORD_AS id ) )? ( nodeinclusion )? ( nodetimes )? ( nodeinfo )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_NODE.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_nodename.nextTree());

                        // TcSimple.g:161:33: ( ^( KEYWORD_AS id ) )?
                        if ( stream_id.hasNext()||stream_KEYWORD_AS.hasNext() ) {
                            // TcSimple.g:161:33: ^( KEYWORD_AS id )
                            {
                            TcAst root_2 = (TcAst)adaptor.nil();
                            root_2 = (TcAst)adaptor.becomeRoot(
                            stream_KEYWORD_AS.nextNode()
                            , root_2);

                            adaptor.addChild(root_2, stream_id.nextTree());

                            adaptor.addChild(root_1, root_2);
                            }

                        }
                        stream_id.reset();
                        stream_KEYWORD_AS.reset();

                        // TcSimple.g:161:51: ( nodeinclusion )?
                        if ( stream_nodeinclusion.hasNext() ) {
                            adaptor.addChild(root_1, stream_nodeinclusion.nextTree());

                        }
                        stream_nodeinclusion.reset();

                        // TcSimple.g:161:66: ( nodetimes )?
                        if ( stream_nodetimes.hasNext() ) {
                            adaptor.addChild(root_1, stream_nodetimes.nextTree());

                        }
                        stream_nodetimes.reset();

                        // TcSimple.g:161:77: ( nodeinfo )*
                        while ( stream_nodeinfo.hasNext() ) {
                            adaptor.addChild(root_1, stream_nodeinfo.nextTree());

                        }
                        stream_nodeinfo.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // TcSimple.g:162:4: KEYWORD_LINK linkpath SEMICOLON
                    {
                    KEYWORD_LINK38=(Token)match(input,KEYWORD_LINK,FOLLOW_KEYWORD_LINK_in_nodeinfo1702);  
                    stream_KEYWORD_LINK.add(KEYWORD_LINK38);


                    pushFollow(FOLLOW_linkpath_in_nodeinfo1704);
                    linkpath39=linkpath();

                    state._fsp--;

                    stream_linkpath.add(linkpath39.getTree());

                    SEMICOLON40=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_nodeinfo1706);  
                    stream_SEMICOLON.add(SEMICOLON40);


                    // AST REWRITE
                    // elements: KEYWORD_LINK, linkpath
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 162:37: -> ^( KEYWORD_LINK linkpath )
                    {
                        // TcSimple.g:162:40: ^( KEYWORD_LINK linkpath )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_LINK.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_linkpath.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "nodeinfo"


    public static class nodeinclusion_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "nodeinclusion"
    // TcSimple.g:165:1: nodeinclusion : kw= KEYWORD_IF formula -> ^( TT_INCLUSIONFORMULA[$kw] formula ) ;
    public final TcSimpleParser.nodeinclusion_return nodeinclusion() throws RecognitionException {
        TcSimpleParser.nodeinclusion_return retval = new TcSimpleParser.nodeinclusion_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token kw=null;
        TcSimpleParser.formula_return formula41 =null;


        TcAst kw_tree=null;
        RewriteRuleTokenStream stream_KEYWORD_IF=new RewriteRuleTokenStream(adaptor,"token KEYWORD_IF");
        RewriteRuleSubtreeStream stream_formula=new RewriteRuleSubtreeStream(adaptor,"rule formula");
        try {
            // TcSimple.g:165:14: (kw= KEYWORD_IF formula -> ^( TT_INCLUSIONFORMULA[$kw] formula ) )
            // TcSimple.g:166:4: kw= KEYWORD_IF formula
            {
            kw=(Token)match(input,KEYWORD_IF,FOLLOW_KEYWORD_IF_in_nodeinclusion1802);  
            stream_KEYWORD_IF.add(kw);


            pushFollow(FOLLOW_formula_in_nodeinclusion1804);
            formula41=formula();

            state._fsp--;

            stream_formula.add(formula41.getTree());

            // AST REWRITE
            // elements: formula
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 166:26: -> ^( TT_INCLUSIONFORMULA[$kw] formula )
            {
                // TcSimple.g:166:29: ^( TT_INCLUSIONFORMULA[$kw] formula )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                (TcAst)adaptor.create(TT_INCLUSIONFORMULA, kw)
                , root_1);

                adaptor.addChild(root_1, stream_formula.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "nodeinclusion"


    public static class nodetimes_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "nodetimes"
    // TcSimple.g:169:1: nodetimes : kw= KEYWORD_TIMES formula KEYWORD_AS id -> ^( TT_TIMESINFO[$kw] formula id ) ;
    public final TcSimpleParser.nodetimes_return nodetimes() throws RecognitionException {
        TcSimpleParser.nodetimes_return retval = new TcSimpleParser.nodetimes_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token kw=null;
        Token KEYWORD_AS43=null;
        TcSimpleParser.formula_return formula42 =null;

        TcSimpleParser.id_return id44 =null;


        TcAst kw_tree=null;
        TcAst KEYWORD_AS43_tree=null;
        RewriteRuleTokenStream stream_KEYWORD_AS=new RewriteRuleTokenStream(adaptor,"token KEYWORD_AS");
        RewriteRuleTokenStream stream_KEYWORD_TIMES=new RewriteRuleTokenStream(adaptor,"token KEYWORD_TIMES");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_formula=new RewriteRuleSubtreeStream(adaptor,"rule formula");
        try {
            // TcSimple.g:169:10: (kw= KEYWORD_TIMES formula KEYWORD_AS id -> ^( TT_TIMESINFO[$kw] formula id ) )
            // TcSimple.g:170:4: kw= KEYWORD_TIMES formula KEYWORD_AS id
            {
            kw=(Token)match(input,KEYWORD_TIMES,FOLLOW_KEYWORD_TIMES_in_nodetimes1879);  
            stream_KEYWORD_TIMES.add(kw);


            pushFollow(FOLLOW_formula_in_nodetimes1881);
            formula42=formula();

            state._fsp--;

            stream_formula.add(formula42.getTree());

            KEYWORD_AS43=(Token)match(input,KEYWORD_AS,FOLLOW_KEYWORD_AS_in_nodetimes1883);  
            stream_KEYWORD_AS.add(KEYWORD_AS43);


            pushFollow(FOLLOW_id_in_nodetimes1885);
            id44=id();

            state._fsp--;

            stream_id.add(id44.getTree());

            // AST REWRITE
            // elements: id, formula
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 170:44: -> ^( TT_TIMESINFO[$kw] formula id )
            {
                // TcSimple.g:170:47: ^( TT_TIMESINFO[$kw] formula id )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                (TcAst)adaptor.create(TT_TIMESINFO, kw)
                , root_1);

                adaptor.addChild(root_1, stream_formula.nextTree());

                adaptor.addChild(root_1, stream_id.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "nodetimes"


    public static class resultdef_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "resultdef"
    // TcSimple.g:173:1: resultdef : id ( arguments )? COMPARE_EQUAL formula SEMICOLON -> ^( TT_RESULTDEF id ( arguments )? formula ) ;
    public final TcSimpleParser.resultdef_return resultdef() throws RecognitionException {
        TcSimpleParser.resultdef_return retval = new TcSimpleParser.resultdef_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token COMPARE_EQUAL47=null;
        Token SEMICOLON49=null;
        TcSimpleParser.id_return id45 =null;

        TcSimpleParser.arguments_return arguments46 =null;

        TcSimpleParser.formula_return formula48 =null;


        TcAst COMPARE_EQUAL47_tree=null;
        TcAst SEMICOLON49_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_COMPARE_EQUAL=new RewriteRuleTokenStream(adaptor,"token COMPARE_EQUAL");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_arguments=new RewriteRuleSubtreeStream(adaptor,"rule arguments");
        RewriteRuleSubtreeStream stream_formula=new RewriteRuleSubtreeStream(adaptor,"rule formula");
        try {
            // TcSimple.g:173:10: ( id ( arguments )? COMPARE_EQUAL formula SEMICOLON -> ^( TT_RESULTDEF id ( arguments )? formula ) )
            // TcSimple.g:174:5: id ( arguments )? COMPARE_EQUAL formula SEMICOLON
            {
            pushFollow(FOLLOW_id_in_resultdef1974);
            id45=id();

            state._fsp--;

            stream_id.add(id45.getTree());

            // TcSimple.g:174:8: ( arguments )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==PAREN_OPEN) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // TcSimple.g:174:8: arguments
                    {
                    pushFollow(FOLLOW_arguments_in_resultdef1976);
                    arguments46=arguments();

                    state._fsp--;

                    stream_arguments.add(arguments46.getTree());

                    }
                    break;

            }


            COMPARE_EQUAL47=(Token)match(input,COMPARE_EQUAL,FOLLOW_COMPARE_EQUAL_in_resultdef1980);  
            stream_COMPARE_EQUAL.add(COMPARE_EQUAL47);


            pushFollow(FOLLOW_formula_in_resultdef1982);
            formula48=formula();

            state._fsp--;

            stream_formula.add(formula48.getTree());

            SEMICOLON49=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_resultdef1984);  
            stream_SEMICOLON.add(SEMICOLON49);


            // AST REWRITE
            // elements: id, formula, arguments
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 174:54: -> ^( TT_RESULTDEF id ( arguments )? formula )
            {
                // TcSimple.g:174:57: ^( TT_RESULTDEF id ( arguments )? formula )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                (TcAst)adaptor.create(TT_RESULTDEF, "TT_RESULTDEF")
                , root_1);

                adaptor.addChild(root_1, stream_id.nextTree());

                // TcSimple.g:174:75: ( arguments )?
                if ( stream_arguments.hasNext() ) {
                    adaptor.addChild(root_1, stream_arguments.nextTree());

                }
                stream_arguments.reset();

                adaptor.addChild(root_1, stream_formula.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "resultdef"


    public static class arguments_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "arguments"
    // TcSimple.g:177:1: arguments : PAREN_OPEN id ( COMMA id )* PAREN_CLOSE -> ^( TT_ARGDEF ( id )* ) ;
    public final TcSimpleParser.arguments_return arguments() throws RecognitionException {
        TcSimpleParser.arguments_return retval = new TcSimpleParser.arguments_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token PAREN_OPEN50=null;
        Token COMMA52=null;
        Token PAREN_CLOSE54=null;
        TcSimpleParser.id_return id51 =null;

        TcSimpleParser.id_return id53 =null;


        TcAst PAREN_OPEN50_tree=null;
        TcAst COMMA52_tree=null;
        TcAst PAREN_CLOSE54_tree=null;
        RewriteRuleTokenStream stream_PAREN_OPEN=new RewriteRuleTokenStream(adaptor,"token PAREN_OPEN");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_PAREN_CLOSE=new RewriteRuleTokenStream(adaptor,"token PAREN_CLOSE");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        try {
            // TcSimple.g:177:18: ( PAREN_OPEN id ( COMMA id )* PAREN_CLOSE -> ^( TT_ARGDEF ( id )* ) )
            // TcSimple.g:177:20: PAREN_OPEN id ( COMMA id )* PAREN_CLOSE
            {
            PAREN_OPEN50=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_arguments2100);  
            stream_PAREN_OPEN.add(PAREN_OPEN50);


            pushFollow(FOLLOW_id_in_arguments2102);
            id51=id();

            state._fsp--;

            stream_id.add(id51.getTree());

            // TcSimple.g:177:34: ( COMMA id )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==COMMA) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // TcSimple.g:177:35: COMMA id
            	    {
            	    COMMA52=(Token)match(input,COMMA,FOLLOW_COMMA_in_arguments2105);  
            	    stream_COMMA.add(COMMA52);


            	    pushFollow(FOLLOW_id_in_arguments2107);
            	    id53=id();

            	    state._fsp--;

            	    stream_id.add(id53.getTree());

            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);


            PAREN_CLOSE54=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_arguments2111);  
            stream_PAREN_CLOSE.add(PAREN_CLOSE54);


            // AST REWRITE
            // elements: id
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 177:58: -> ^( TT_ARGDEF ( id )* )
            {
                // TcSimple.g:177:61: ^( TT_ARGDEF ( id )* )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                (TcAst)adaptor.create(TT_ARGDEF, "TT_ARGDEF")
                , root_1);

                // TcSimple.g:177:73: ( id )*
                while ( stream_id.hasNext() ) {
                    adaptor.addChild(root_1, stream_id.nextTree());

                }
                stream_id.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "arguments"


    public static class id_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "id"
    // TcSimple.g:179:1: id : ( ID -> ID |kw= keywordAsId -> ID[((CommonTree)kw.tree).getText()] );
    public final TcSimpleParser.id_return id() throws RecognitionException {
        TcSimpleParser.id_return retval = new TcSimpleParser.id_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token ID55=null;
        TcSimpleParser.keywordAsId_return kw =null;


        TcAst ID55_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_keywordAsId=new RewriteRuleSubtreeStream(adaptor,"rule keywordAsId");
        try {
            // TcSimple.g:179:3: ( ID -> ID |kw= keywordAsId -> ID[((CommonTree)kw.tree).getText()] )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==ID) ) {
                alt16=1;
            }
            else if ( ((LA16_0 >= KEYWORD_AS && LA16_0 <= KEYWORD_INTERPOL)||(LA16_0 >= KEYWORD_LOOKDOWNX && LA16_0 <= KEYWORD_TABLE)||(LA16_0 >= KEYWORD_TABROWS && LA16_0 <= KEYWORD_THEN)||(LA16_0 >= KEYWORD_TREE && LA16_0 <= KEYWORD_WHEN)) ) {
                alt16=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;

            }
            switch (alt16) {
                case 1 :
                    // TcSimple.g:180:4: ID
                    {
                    ID55=(Token)match(input,ID,FOLLOW_ID_in_id2214);  
                    stream_ID.add(ID55);


                    // AST REWRITE
                    // elements: ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 180:20: -> ID
                    {
                        adaptor.addChild(root_0, 
                        stream_ID.nextNode()
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // TcSimple.g:181:4: kw= keywordAsId
                    {
                    pushFollow(FOLLOW_keywordAsId_in_id2238);
                    kw=keywordAsId();

                    state._fsp--;

                    stream_keywordAsId.add(kw.getTree());

                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 181:20: -> ID[((CommonTree)kw.tree).getText()]
                    {
                        adaptor.addChild(root_0, 
                        (TcAst)adaptor.create(ID, ((CommonTree)kw.tree).getText())
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "id"


    public static class keywordAsId_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "keywordAsId"
    // TcSimple.g:184:1: keywordAsId : ( KEYWORD_AS | KEYWORD_TABLE | KEYWORD_TREE | KEYWORD_CALC | KEYWORD_INPUT | KEYWORD_FUNC | KEYWORD_NODE | KEYWORD_IF | KEYWORD_THEN | KEYWORD_ELSE | KEYWORD_ENDIF | KEYWORD_CASE | KEYWORD_WHEN | KEYWORD_DEFAULT | KEYWORD_ENDCASE | KEYWORD_COLLATE | KEYWORD_EXTRACT | KEYWORD_SUMX | KEYWORD_PRODX | KEYWORD_VECTORX | KEYWORD_CELL | KEYWORD_CELLX | KEYWORD_EXISTS | KEYWORD_INTERPOL | KEYWORD_TABCOLS | KEYWORD_TABROWS | KEYWORD_LOOKUP | KEYWORD_LOOKUPX | KEYWORD_LOOKDOWNX | KEYWORD_FUNCREF | KEYWORD_DOCALL | KEYWORD_COUNTERLIST );
    public final TcSimpleParser.keywordAsId_return keywordAsId() throws RecognitionException {
        TcSimpleParser.keywordAsId_return retval = new TcSimpleParser.keywordAsId_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token set56=null;

        TcAst set56_tree=null;

        try {
            // TcSimple.g:184:12: ( KEYWORD_AS | KEYWORD_TABLE | KEYWORD_TREE | KEYWORD_CALC | KEYWORD_INPUT | KEYWORD_FUNC | KEYWORD_NODE | KEYWORD_IF | KEYWORD_THEN | KEYWORD_ELSE | KEYWORD_ENDIF | KEYWORD_CASE | KEYWORD_WHEN | KEYWORD_DEFAULT | KEYWORD_ENDCASE | KEYWORD_COLLATE | KEYWORD_EXTRACT | KEYWORD_SUMX | KEYWORD_PRODX | KEYWORD_VECTORX | KEYWORD_CELL | KEYWORD_CELLX | KEYWORD_EXISTS | KEYWORD_INTERPOL | KEYWORD_TABCOLS | KEYWORD_TABROWS | KEYWORD_LOOKUP | KEYWORD_LOOKUPX | KEYWORD_LOOKDOWNX | KEYWORD_FUNCREF | KEYWORD_DOCALL | KEYWORD_COUNTERLIST )
            // TcSimple.g:
            {
            root_0 = (TcAst)adaptor.nil();


            set56=(Token)input.LT(1);

            if ( (input.LA(1) >= KEYWORD_AS && input.LA(1) <= KEYWORD_INTERPOL)||(input.LA(1) >= KEYWORD_LOOKDOWNX && input.LA(1) <= KEYWORD_TABLE)||(input.LA(1) >= KEYWORD_TABROWS && input.LA(1) <= KEYWORD_THEN)||(input.LA(1) >= KEYWORD_TREE && input.LA(1) <= KEYWORD_WHEN) ) {
                input.consume();
                adaptor.addChild(root_0, 
                (TcAst)adaptor.create(set56)
                );
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "keywordAsId"


    public static class constantorid_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "constantorid"
    // TcSimple.g:219:1: constantorid : ( constant | id );
    public final TcSimpleParser.constantorid_return constantorid() throws RecognitionException {
        TcSimpleParser.constantorid_return retval = new TcSimpleParser.constantorid_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        TcSimpleParser.constant_return constant57 =null;

        TcSimpleParser.id_return id58 =null;



        try {
            // TcSimple.g:219:13: ( constant | id )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==NUMBER||LA17_0==STRING) ) {
                alt17=1;
            }
            else if ( ((LA17_0 >= ID && LA17_0 <= KEYWORD_INTERPOL)||(LA17_0 >= KEYWORD_LOOKDOWNX && LA17_0 <= KEYWORD_TABLE)||(LA17_0 >= KEYWORD_TABROWS && LA17_0 <= KEYWORD_THEN)||(LA17_0 >= KEYWORD_TREE && LA17_0 <= KEYWORD_WHEN)) ) {
                alt17=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;

            }
            switch (alt17) {
                case 1 :
                    // TcSimple.g:220:3: constant
                    {
                    root_0 = (TcAst)adaptor.nil();


                    pushFollow(FOLLOW_constant_in_constantorid2495);
                    constant57=constant();

                    state._fsp--;

                    adaptor.addChild(root_0, constant57.getTree());

                    }
                    break;
                case 2 :
                    // TcSimple.g:221:3: id
                    {
                    root_0 = (TcAst)adaptor.nil();


                    pushFollow(FOLLOW_id_in_constantorid2499);
                    id58=id();

                    state._fsp--;

                    adaptor.addChild(root_0, id58.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "constantorid"


    public static class tableline_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "tableline"
    // TcSimple.g:224:1: tableline : tablecell ( COMMA tablecell )* SEMICOLON -> ^( TT_TABLELINE ( tablecell )* ) ;
    public final TcSimpleParser.tableline_return tableline() throws RecognitionException {
        TcSimpleParser.tableline_return retval = new TcSimpleParser.tableline_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token COMMA60=null;
        Token SEMICOLON62=null;
        TcSimpleParser.tablecell_return tablecell59 =null;

        TcSimpleParser.tablecell_return tablecell61 =null;


        TcAst COMMA60_tree=null;
        TcAst SEMICOLON62_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleSubtreeStream stream_tablecell=new RewriteRuleSubtreeStream(adaptor,"rule tablecell");
        try {
            // TcSimple.g:224:10: ( tablecell ( COMMA tablecell )* SEMICOLON -> ^( TT_TABLELINE ( tablecell )* ) )
            // TcSimple.g:225:4: tablecell ( COMMA tablecell )* SEMICOLON
            {
            pushFollow(FOLLOW_tablecell_in_tableline2568);
            tablecell59=tablecell();

            state._fsp--;

            stream_tablecell.add(tablecell59.getTree());

            // TcSimple.g:225:14: ( COMMA tablecell )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==COMMA) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // TcSimple.g:225:15: COMMA tablecell
            	    {
            	    COMMA60=(Token)match(input,COMMA,FOLLOW_COMMA_in_tableline2571);  
            	    stream_COMMA.add(COMMA60);


            	    pushFollow(FOLLOW_tablecell_in_tableline2573);
            	    tablecell61=tablecell();

            	    state._fsp--;

            	    stream_tablecell.add(tablecell61.getTree());

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);


            SEMICOLON62=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_tableline2577);  
            stream_SEMICOLON.add(SEMICOLON62);


            // AST REWRITE
            // elements: tablecell
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 225:50: -> ^( TT_TABLELINE ( tablecell )* )
            {
                // TcSimple.g:225:54: ^( TT_TABLELINE ( tablecell )* )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                (TcAst)adaptor.create(TT_TABLELINE, "TT_TABLELINE")
                , root_1);

                // TcSimple.g:225:69: ( tablecell )*
                while ( stream_tablecell.hasNext() ) {
                    adaptor.addChild(root_1, stream_tablecell.nextTree());

                }
                stream_tablecell.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "tableline"


    public static class tablecell_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "tablecell"
    // TcSimple.g:228:1: tablecell : ( constant | id );
    public final TcSimpleParser.tablecell_return tablecell() throws RecognitionException {
        TcSimpleParser.tablecell_return retval = new TcSimpleParser.tablecell_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        TcSimpleParser.constant_return constant63 =null;

        TcSimpleParser.id_return id64 =null;



        try {
            // TcSimple.g:228:10: ( constant | id )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==NUMBER||LA19_0==STRING) ) {
                alt19=1;
            }
            else if ( ((LA19_0 >= ID && LA19_0 <= KEYWORD_INTERPOL)||(LA19_0 >= KEYWORD_LOOKDOWNX && LA19_0 <= KEYWORD_TABLE)||(LA19_0 >= KEYWORD_TABROWS && LA19_0 <= KEYWORD_THEN)||(LA19_0 >= KEYWORD_TREE && LA19_0 <= KEYWORD_WHEN)) ) {
                alt19=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;

            }
            switch (alt19) {
                case 1 :
                    // TcSimple.g:229:4: constant
                    {
                    root_0 = (TcAst)adaptor.nil();


                    pushFollow(FOLLOW_constant_in_tablecell2641);
                    constant63=constant();

                    state._fsp--;

                    adaptor.addChild(root_0, constant63.getTree());

                    }
                    break;
                case 2 :
                    // TcSimple.g:229:13: id
                    {
                    root_0 = (TcAst)adaptor.nil();


                    pushFollow(FOLLOW_id_in_tablecell2643);
                    id64=id();

                    state._fsp--;

                    adaptor.addChild(root_0, id64.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "tablecell"


    public static class nodename_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "nodename"
    // TcSimple.g:232:1: nodename : ( constant | id );
    public final TcSimpleParser.nodename_return nodename() throws RecognitionException {
        TcSimpleParser.nodename_return retval = new TcSimpleParser.nodename_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        TcSimpleParser.constant_return constant65 =null;

        TcSimpleParser.id_return id66 =null;



        try {
            // TcSimple.g:232:9: ( constant | id )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==NUMBER||LA20_0==STRING) ) {
                alt20=1;
            }
            else if ( ((LA20_0 >= ID && LA20_0 <= KEYWORD_INTERPOL)||(LA20_0 >= KEYWORD_LOOKDOWNX && LA20_0 <= KEYWORD_TABLE)||(LA20_0 >= KEYWORD_TABROWS && LA20_0 <= KEYWORD_THEN)||(LA20_0 >= KEYWORD_TREE && LA20_0 <= KEYWORD_WHEN)) ) {
                alt20=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;

            }
            switch (alt20) {
                case 1 :
                    // TcSimple.g:233:4: constant
                    {
                    root_0 = (TcAst)adaptor.nil();


                    pushFollow(FOLLOW_constant_in_nodename2690);
                    constant65=constant();

                    state._fsp--;

                    adaptor.addChild(root_0, constant65.getTree());

                    }
                    break;
                case 2 :
                    // TcSimple.g:233:13: id
                    {
                    root_0 = (TcAst)adaptor.nil();


                    pushFollow(FOLLOW_id_in_nodename2692);
                    id66=id();

                    state._fsp--;

                    adaptor.addChild(root_0, id66.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "nodename"


    public static class nodepath_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "nodepath"
    // TcSimple.g:235:1: nodepath : id ( DOT id )* -> ^( TT_NODEPATH ( id )* ) ;
    public final TcSimpleParser.nodepath_return nodepath() throws RecognitionException {
        TcSimpleParser.nodepath_return retval = new TcSimpleParser.nodepath_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token DOT68=null;
        TcSimpleParser.id_return id67 =null;

        TcSimpleParser.id_return id69 =null;


        TcAst DOT68_tree=null;
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        try {
            // TcSimple.g:235:9: ( id ( DOT id )* -> ^( TT_NODEPATH ( id )* ) )
            // TcSimple.g:236:4: id ( DOT id )*
            {
            pushFollow(FOLLOW_id_in_nodepath2713);
            id67=id();

            state._fsp--;

            stream_id.add(id67.getTree());

            // TcSimple.g:236:7: ( DOT id )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==DOT) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // TcSimple.g:236:8: DOT id
            	    {
            	    DOT68=(Token)match(input,DOT,FOLLOW_DOT_in_nodepath2716);  
            	    stream_DOT.add(DOT68);


            	    pushFollow(FOLLOW_id_in_nodepath2718);
            	    id69=id();

            	    state._fsp--;

            	    stream_id.add(id69.getTree());

            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);


            // AST REWRITE
            // elements: id
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 236:20: -> ^( TT_NODEPATH ( id )* )
            {
                // TcSimple.g:236:23: ^( TT_NODEPATH ( id )* )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                (TcAst)adaptor.create(TT_NODEPATH, "TT_NODEPATH")
                , root_1);

                // TcSimple.g:236:37: ( id )*
                while ( stream_id.hasNext() ) {
                    adaptor.addChild(root_1, stream_id.nextTree());

                }
                stream_id.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "nodepath"


    public static class linkpath_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "linkpath"
    // TcSimple.g:239:1: linkpath : id ( DOT linkpart )* -> ^( TT_NODEPATH id ( linkpart )* ) ;
    public final TcSimpleParser.linkpath_return linkpath() throws RecognitionException {
        TcSimpleParser.linkpath_return retval = new TcSimpleParser.linkpath_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token DOT71=null;
        TcSimpleParser.id_return id70 =null;

        TcSimpleParser.linkpart_return linkpart72 =null;


        TcAst DOT71_tree=null;
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_linkpart=new RewriteRuleSubtreeStream(adaptor,"rule linkpart");
        try {
            // TcSimple.g:239:9: ( id ( DOT linkpart )* -> ^( TT_NODEPATH id ( linkpart )* ) )
            // TcSimple.g:240:4: id ( DOT linkpart )*
            {
            pushFollow(FOLLOW_id_in_linkpath2751);
            id70=id();

            state._fsp--;

            stream_id.add(id70.getTree());

            // TcSimple.g:240:7: ( DOT linkpart )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==DOT) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // TcSimple.g:240:8: DOT linkpart
            	    {
            	    DOT71=(Token)match(input,DOT,FOLLOW_DOT_in_linkpath2754);  
            	    stream_DOT.add(DOT71);


            	    pushFollow(FOLLOW_linkpart_in_linkpath2756);
            	    linkpart72=linkpart();

            	    state._fsp--;

            	    stream_linkpart.add(linkpart72.getTree());

            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);


            // AST REWRITE
            // elements: id, linkpart
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 240:26: -> ^( TT_NODEPATH id ( linkpart )* )
            {
                // TcSimple.g:240:29: ^( TT_NODEPATH id ( linkpart )* )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                (TcAst)adaptor.create(TT_NODEPATH, "TT_NODEPATH")
                , root_1);

                adaptor.addChild(root_1, stream_id.nextTree());

                // TcSimple.g:240:46: ( linkpart )*
                while ( stream_linkpart.hasNext() ) {
                    adaptor.addChild(root_1, stream_linkpart.nextTree());

                }
                stream_linkpart.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "linkpath"


    public static class linkpart_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "linkpart"
    // TcSimple.g:243:1: linkpart : ( id | STRING | ASTERISK | DOUBLEASTERISK );
    public final TcSimpleParser.linkpart_return linkpart() throws RecognitionException {
        TcSimpleParser.linkpart_return retval = new TcSimpleParser.linkpart_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token STRING74=null;
        Token ASTERISK75=null;
        Token DOUBLEASTERISK76=null;
        TcSimpleParser.id_return id73 =null;


        TcAst STRING74_tree=null;
        TcAst ASTERISK75_tree=null;
        TcAst DOUBLEASTERISK76_tree=null;

        try {
            // TcSimple.g:243:9: ( id | STRING | ASTERISK | DOUBLEASTERISK )
            int alt23=4;
            switch ( input.LA(1) ) {
            case ID:
            case KEYWORD_AS:
            case KEYWORD_CALC:
            case KEYWORD_CASE:
            case KEYWORD_CELL:
            case KEYWORD_CELLX:
            case KEYWORD_COLLATE:
            case KEYWORD_COUNTERLIST:
            case KEYWORD_DEFAULT:
            case KEYWORD_DOCALL:
            case KEYWORD_ELSE:
            case KEYWORD_ENDCASE:
            case KEYWORD_ENDIF:
            case KEYWORD_EXISTS:
            case KEYWORD_EXTRACT:
            case KEYWORD_FUNC:
            case KEYWORD_FUNCREF:
            case KEYWORD_IF:
            case KEYWORD_INPUT:
            case KEYWORD_INTERPOL:
            case KEYWORD_LOOKDOWNX:
            case KEYWORD_LOOKUP:
            case KEYWORD_LOOKUPX:
            case KEYWORD_NODE:
            case KEYWORD_PRODX:
            case KEYWORD_SUMX:
            case KEYWORD_TABCOLS:
            case KEYWORD_TABLE:
            case KEYWORD_TABROWS:
            case KEYWORD_THEN:
            case KEYWORD_TREE:
            case KEYWORD_VECTORX:
            case KEYWORD_WHEN:
                {
                alt23=1;
                }
                break;
            case STRING:
                {
                alt23=2;
                }
                break;
            case ASTERISK:
                {
                alt23=3;
                }
                break;
            case DOUBLEASTERISK:
                {
                alt23=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;

            }

            switch (alt23) {
                case 1 :
                    // TcSimple.g:244:4: id
                    {
                    root_0 = (TcAst)adaptor.nil();


                    pushFollow(FOLLOW_id_in_linkpart2831);
                    id73=id();

                    state._fsp--;

                    adaptor.addChild(root_0, id73.getTree());

                    }
                    break;
                case 2 :
                    // TcSimple.g:245:4: STRING
                    {
                    root_0 = (TcAst)adaptor.nil();


                    STRING74=(Token)match(input,STRING,FOLLOW_STRING_in_linkpart2879); 
                    STRING74_tree = 
                    (TcAst)adaptor.create(STRING74)
                    ;
                    adaptor.addChild(root_0, STRING74_tree);


                    }
                    break;
                case 3 :
                    // TcSimple.g:246:4: ASTERISK
                    {
                    root_0 = (TcAst)adaptor.nil();


                    ASTERISK75=(Token)match(input,ASTERISK,FOLLOW_ASTERISK_in_linkpart2893); 
                    ASTERISK75_tree = 
                    (TcAst)adaptor.create(ASTERISK75)
                    ;
                    adaptor.addChild(root_0, ASTERISK75_tree);


                    }
                    break;
                case 4 :
                    // TcSimple.g:247:4: DOUBLEASTERISK
                    {
                    root_0 = (TcAst)adaptor.nil();


                    DOUBLEASTERISK76=(Token)match(input,DOUBLEASTERISK,FOLLOW_DOUBLEASTERISK_in_linkpart2905); 
                    DOUBLEASTERISK76_tree = 
                    (TcAst)adaptor.create(DOUBLEASTERISK76)
                    ;
                    adaptor.addChild(root_0, DOUBLEASTERISK76_tree);


                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "linkpart"


    public static class colnames_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "colnames"
    // TcSimple.g:250:1: colnames : colname ( COMMA colname )* -> ^( TT_IDLIST ( colname )* ) ;
    public final TcSimpleParser.colnames_return colnames() throws RecognitionException {
        TcSimpleParser.colnames_return retval = new TcSimpleParser.colnames_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token COMMA78=null;
        TcSimpleParser.colname_return colname77 =null;

        TcSimpleParser.colname_return colname79 =null;


        TcAst COMMA78_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleSubtreeStream stream_colname=new RewriteRuleSubtreeStream(adaptor,"rule colname");
        try {
            // TcSimple.g:250:9: ( colname ( COMMA colname )* -> ^( TT_IDLIST ( colname )* ) )
            // TcSimple.g:251:4: colname ( COMMA colname )*
            {
            pushFollow(FOLLOW_colname_in_colnames2920);
            colname77=colname();

            state._fsp--;

            stream_colname.add(colname77.getTree());

            // TcSimple.g:251:12: ( COMMA colname )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==COMMA) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // TcSimple.g:251:13: COMMA colname
            	    {
            	    COMMA78=(Token)match(input,COMMA,FOLLOW_COMMA_in_colnames2923);  
            	    stream_COMMA.add(COMMA78);


            	    pushFollow(FOLLOW_colname_in_colnames2925);
            	    colname79=colname();

            	    state._fsp--;

            	    stream_colname.add(colname79.getTree());

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);


            // AST REWRITE
            // elements: colname
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 251:54: -> ^( TT_IDLIST ( colname )* )
            {
                // TcSimple.g:251:57: ^( TT_IDLIST ( colname )* )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                (TcAst)adaptor.create(TT_IDLIST, "TT_IDLIST")
                , root_1);

                // TcSimple.g:251:69: ( colname )*
                while ( stream_colname.hasNext() ) {
                    adaptor.addChild(root_1, stream_colname.nextTree());

                }
                stream_colname.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "colnames"


    public static class colname_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "colname"
    // TcSimple.g:254:1: colname : ( id | NUMBER | STRING );
    public final TcSimpleParser.colname_return colname() throws RecognitionException {
        TcSimpleParser.colname_return retval = new TcSimpleParser.colname_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token NUMBER81=null;
        Token STRING82=null;
        TcSimpleParser.id_return id80 =null;


        TcAst NUMBER81_tree=null;
        TcAst STRING82_tree=null;

        try {
            // TcSimple.g:254:8: ( id | NUMBER | STRING )
            int alt25=3;
            switch ( input.LA(1) ) {
            case ID:
            case KEYWORD_AS:
            case KEYWORD_CALC:
            case KEYWORD_CASE:
            case KEYWORD_CELL:
            case KEYWORD_CELLX:
            case KEYWORD_COLLATE:
            case KEYWORD_COUNTERLIST:
            case KEYWORD_DEFAULT:
            case KEYWORD_DOCALL:
            case KEYWORD_ELSE:
            case KEYWORD_ENDCASE:
            case KEYWORD_ENDIF:
            case KEYWORD_EXISTS:
            case KEYWORD_EXTRACT:
            case KEYWORD_FUNC:
            case KEYWORD_FUNCREF:
            case KEYWORD_IF:
            case KEYWORD_INPUT:
            case KEYWORD_INTERPOL:
            case KEYWORD_LOOKDOWNX:
            case KEYWORD_LOOKUP:
            case KEYWORD_LOOKUPX:
            case KEYWORD_NODE:
            case KEYWORD_PRODX:
            case KEYWORD_SUMX:
            case KEYWORD_TABCOLS:
            case KEYWORD_TABLE:
            case KEYWORD_TABROWS:
            case KEYWORD_THEN:
            case KEYWORD_TREE:
            case KEYWORD_VECTORX:
            case KEYWORD_WHEN:
                {
                alt25=1;
                }
                break;
            case NUMBER:
                {
                alt25=2;
                }
                break;
            case STRING:
                {
                alt25=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;

            }

            switch (alt25) {
                case 1 :
                    // TcSimple.g:255:4: id
                    {
                    root_0 = (TcAst)adaptor.nil();


                    pushFollow(FOLLOW_id_in_colname3048);
                    id80=id();

                    state._fsp--;

                    adaptor.addChild(root_0, id80.getTree());

                    }
                    break;
                case 2 :
                    // TcSimple.g:256:4: NUMBER
                    {
                    root_0 = (TcAst)adaptor.nil();


                    NUMBER81=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_colname3054); 
                    NUMBER81_tree = 
                    (TcAst)adaptor.create(NUMBER81)
                    ;
                    adaptor.addChild(root_0, NUMBER81_tree);


                    }
                    break;
                case 3 :
                    // TcSimple.g:257:4: STRING
                    {
                    root_0 = (TcAst)adaptor.nil();


                    STRING82=(Token)match(input,STRING,FOLLOW_STRING_in_colname3059); 
                    STRING82_tree = 
                    (TcAst)adaptor.create(STRING82)
                    ;
                    adaptor.addChild(root_0, STRING82_tree);


                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "colname"


    public static class vpmsformula_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "vpmsformula"
    // TcSimple.g:261:1: vpmsformula : formula EOF ;
    public final TcSimpleParser.vpmsformula_return vpmsformula() throws RecognitionException {
        TcSimpleParser.vpmsformula_return retval = new TcSimpleParser.vpmsformula_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token EOF84=null;
        TcSimpleParser.formula_return formula83 =null;


        TcAst EOF84_tree=null;

        try {
            // TcSimple.g:261:12: ( formula EOF )
            // TcSimple.g:261:14: formula EOF
            {
            root_0 = (TcAst)adaptor.nil();


            pushFollow(FOLLOW_formula_in_vpmsformula3114);
            formula83=formula();

            state._fsp--;

            adaptor.addChild(root_0, formula83.getTree());

            EOF84=(Token)match(input,EOF,FOLLOW_EOF_in_vpmsformula3116); 
            EOF84_tree = 
            (TcAst)adaptor.create(EOF84)
            ;
            adaptor.addChild(root_0, EOF84_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "vpmsformula"


    public static class formula_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formula"
    // TcSimple.g:263:1: formula : formula2 ( QUESTIONMARK ^ formula2 COLON ! formula2 )* ;
    public final TcSimpleParser.formula_return formula() throws RecognitionException {
        TcSimpleParser.formula_return retval = new TcSimpleParser.formula_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token QUESTIONMARK86=null;
        Token COLON88=null;
        TcSimpleParser.formula2_return formula285 =null;

        TcSimpleParser.formula2_return formula287 =null;

        TcSimpleParser.formula2_return formula289 =null;


        TcAst QUESTIONMARK86_tree=null;
        TcAst COLON88_tree=null;

        try {
            // TcSimple.g:263:8: ( formula2 ( QUESTIONMARK ^ formula2 COLON ! formula2 )* )
            // TcSimple.g:263:11: formula2 ( QUESTIONMARK ^ formula2 COLON ! formula2 )*
            {
            root_0 = (TcAst)adaptor.nil();


            pushFollow(FOLLOW_formula2_in_formula3124);
            formula285=formula2();

            state._fsp--;

            adaptor.addChild(root_0, formula285.getTree());

            // TcSimple.g:263:20: ( QUESTIONMARK ^ formula2 COLON ! formula2 )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==QUESTIONMARK) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // TcSimple.g:263:21: QUESTIONMARK ^ formula2 COLON ! formula2
            	    {
            	    QUESTIONMARK86=(Token)match(input,QUESTIONMARK,FOLLOW_QUESTIONMARK_in_formula3127); 
            	    QUESTIONMARK86_tree = 
            	    (TcAst)adaptor.create(QUESTIONMARK86)
            	    ;
            	    root_0 = (TcAst)adaptor.becomeRoot(QUESTIONMARK86_tree, root_0);


            	    pushFollow(FOLLOW_formula2_in_formula3130);
            	    formula287=formula2();

            	    state._fsp--;

            	    adaptor.addChild(root_0, formula287.getTree());

            	    COLON88=(Token)match(input,COLON,FOLLOW_COLON_in_formula3132); 

            	    pushFollow(FOLLOW_formula2_in_formula3135);
            	    formula289=formula2();

            	    state._fsp--;

            	    adaptor.addChild(root_0, formula289.getTree());

            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "formula"


    public static class formula2_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formula2"
    // TcSimple.g:264:1: formula2 : formula3 ( LOGICAL_OR ^ formula3 )* ;
    public final TcSimpleParser.formula2_return formula2() throws RecognitionException {
        TcSimpleParser.formula2_return retval = new TcSimpleParser.formula2_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token LOGICAL_OR91=null;
        TcSimpleParser.formula3_return formula390 =null;

        TcSimpleParser.formula3_return formula392 =null;


        TcAst LOGICAL_OR91_tree=null;

        try {
            // TcSimple.g:264:9: ( formula3 ( LOGICAL_OR ^ formula3 )* )
            // TcSimple.g:264:11: formula3 ( LOGICAL_OR ^ formula3 )*
            {
            root_0 = (TcAst)adaptor.nil();


            pushFollow(FOLLOW_formula3_in_formula23143);
            formula390=formula3();

            state._fsp--;

            adaptor.addChild(root_0, formula390.getTree());

            // TcSimple.g:264:20: ( LOGICAL_OR ^ formula3 )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==LOGICAL_OR) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // TcSimple.g:264:21: LOGICAL_OR ^ formula3
            	    {
            	    LOGICAL_OR91=(Token)match(input,LOGICAL_OR,FOLLOW_LOGICAL_OR_in_formula23146); 
            	    LOGICAL_OR91_tree = 
            	    (TcAst)adaptor.create(LOGICAL_OR91)
            	    ;
            	    root_0 = (TcAst)adaptor.becomeRoot(LOGICAL_OR91_tree, root_0);


            	    pushFollow(FOLLOW_formula3_in_formula23149);
            	    formula392=formula3();

            	    state._fsp--;

            	    adaptor.addChild(root_0, formula392.getTree());

            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "formula2"


    public static class formula3_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formula3"
    // TcSimple.g:265:1: formula3 : formula4 ( LOGICAL_AND ^ formula4 )* ;
    public final TcSimpleParser.formula3_return formula3() throws RecognitionException {
        TcSimpleParser.formula3_return retval = new TcSimpleParser.formula3_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token LOGICAL_AND94=null;
        TcSimpleParser.formula4_return formula493 =null;

        TcSimpleParser.formula4_return formula495 =null;


        TcAst LOGICAL_AND94_tree=null;

        try {
            // TcSimple.g:265:9: ( formula4 ( LOGICAL_AND ^ formula4 )* )
            // TcSimple.g:265:11: formula4 ( LOGICAL_AND ^ formula4 )*
            {
            root_0 = (TcAst)adaptor.nil();


            pushFollow(FOLLOW_formula4_in_formula33157);
            formula493=formula4();

            state._fsp--;

            adaptor.addChild(root_0, formula493.getTree());

            // TcSimple.g:265:20: ( LOGICAL_AND ^ formula4 )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==LOGICAL_AND) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // TcSimple.g:265:21: LOGICAL_AND ^ formula4
            	    {
            	    LOGICAL_AND94=(Token)match(input,LOGICAL_AND,FOLLOW_LOGICAL_AND_in_formula33160); 
            	    LOGICAL_AND94_tree = 
            	    (TcAst)adaptor.create(LOGICAL_AND94)
            	    ;
            	    root_0 = (TcAst)adaptor.becomeRoot(LOGICAL_AND94_tree, root_0);


            	    pushFollow(FOLLOW_formula4_in_formula33163);
            	    formula495=formula4();

            	    state._fsp--;

            	    adaptor.addChild(root_0, formula495.getTree());

            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "formula3"


    public static class formula4_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formula4"
    // TcSimple.g:266:1: formula4 : formula5 ( LOGICAL_XOR ^ formula5 )* ;
    public final TcSimpleParser.formula4_return formula4() throws RecognitionException {
        TcSimpleParser.formula4_return retval = new TcSimpleParser.formula4_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token LOGICAL_XOR97=null;
        TcSimpleParser.formula5_return formula596 =null;

        TcSimpleParser.formula5_return formula598 =null;


        TcAst LOGICAL_XOR97_tree=null;

        try {
            // TcSimple.g:266:9: ( formula5 ( LOGICAL_XOR ^ formula5 )* )
            // TcSimple.g:266:11: formula5 ( LOGICAL_XOR ^ formula5 )*
            {
            root_0 = (TcAst)adaptor.nil();


            pushFollow(FOLLOW_formula5_in_formula43171);
            formula596=formula5();

            state._fsp--;

            adaptor.addChild(root_0, formula596.getTree());

            // TcSimple.g:266:20: ( LOGICAL_XOR ^ formula5 )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==LOGICAL_XOR) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // TcSimple.g:266:21: LOGICAL_XOR ^ formula5
            	    {
            	    LOGICAL_XOR97=(Token)match(input,LOGICAL_XOR,FOLLOW_LOGICAL_XOR_in_formula43174); 
            	    LOGICAL_XOR97_tree = 
            	    (TcAst)adaptor.create(LOGICAL_XOR97)
            	    ;
            	    root_0 = (TcAst)adaptor.becomeRoot(LOGICAL_XOR97_tree, root_0);


            	    pushFollow(FOLLOW_formula5_in_formula43177);
            	    formula598=formula5();

            	    state._fsp--;

            	    adaptor.addChild(root_0, formula598.getTree());

            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "formula4"


    public static class formula5_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formula5"
    // TcSimple.g:267:1: formula5 : formula6 ( comparisonoperator ^ formula6 )* ;
    public final TcSimpleParser.formula5_return formula5() throws RecognitionException {
        TcSimpleParser.formula5_return retval = new TcSimpleParser.formula5_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        TcSimpleParser.formula6_return formula699 =null;

        TcSimpleParser.comparisonoperator_return comparisonoperator100 =null;

        TcSimpleParser.formula6_return formula6101 =null;



        try {
            // TcSimple.g:267:9: ( formula6 ( comparisonoperator ^ formula6 )* )
            // TcSimple.g:267:11: formula6 ( comparisonoperator ^ formula6 )*
            {
            root_0 = (TcAst)adaptor.nil();


            pushFollow(FOLLOW_formula6_in_formula53185);
            formula699=formula6();

            state._fsp--;

            adaptor.addChild(root_0, formula699.getTree());

            // TcSimple.g:267:20: ( comparisonoperator ^ formula6 )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( ((LA30_0 >= COMPARE_BIGGER && LA30_0 <= COMPARE_STR_NOTEQUAL)) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // TcSimple.g:267:21: comparisonoperator ^ formula6
            	    {
            	    pushFollow(FOLLOW_comparisonoperator_in_formula53188);
            	    comparisonoperator100=comparisonoperator();

            	    state._fsp--;

            	    root_0 = (TcAst)adaptor.becomeRoot(comparisonoperator100.getTree(), root_0);

            	    pushFollow(FOLLOW_formula6_in_formula53191);
            	    formula6101=formula6();

            	    state._fsp--;

            	    adaptor.addChild(root_0, formula6101.getTree());

            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "formula5"


    public static class formula6_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formula6"
    // TcSimple.g:268:1: formula6 : formula7 ( STRCAT ^ formula7 )* ;
    public final TcSimpleParser.formula6_return formula6() throws RecognitionException {
        TcSimpleParser.formula6_return retval = new TcSimpleParser.formula6_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token STRCAT103=null;
        TcSimpleParser.formula7_return formula7102 =null;

        TcSimpleParser.formula7_return formula7104 =null;


        TcAst STRCAT103_tree=null;

        try {
            // TcSimple.g:268:9: ( formula7 ( STRCAT ^ formula7 )* )
            // TcSimple.g:268:11: formula7 ( STRCAT ^ formula7 )*
            {
            root_0 = (TcAst)adaptor.nil();


            pushFollow(FOLLOW_formula7_in_formula63199);
            formula7102=formula7();

            state._fsp--;

            adaptor.addChild(root_0, formula7102.getTree());

            // TcSimple.g:268:20: ( STRCAT ^ formula7 )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==STRCAT) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // TcSimple.g:268:21: STRCAT ^ formula7
            	    {
            	    STRCAT103=(Token)match(input,STRCAT,FOLLOW_STRCAT_in_formula63202); 
            	    STRCAT103_tree = 
            	    (TcAst)adaptor.create(STRCAT103)
            	    ;
            	    root_0 = (TcAst)adaptor.becomeRoot(STRCAT103_tree, root_0);


            	    pushFollow(FOLLOW_formula7_in_formula63205);
            	    formula7104=formula7();

            	    state._fsp--;

            	    adaptor.addChild(root_0, formula7104.getTree());

            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "formula6"


    public static class formula7_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formula7"
    // TcSimple.g:269:1: formula7 : formula8 ( ( PLUS | MINUS ) ^ formula8 )* ;
    public final TcSimpleParser.formula7_return formula7() throws RecognitionException {
        TcSimpleParser.formula7_return retval = new TcSimpleParser.formula7_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token set106=null;
        TcSimpleParser.formula8_return formula8105 =null;

        TcSimpleParser.formula8_return formula8107 =null;


        TcAst set106_tree=null;

        try {
            // TcSimple.g:269:9: ( formula8 ( ( PLUS | MINUS ) ^ formula8 )* )
            // TcSimple.g:269:11: formula8 ( ( PLUS | MINUS ) ^ formula8 )*
            {
            root_0 = (TcAst)adaptor.nil();


            pushFollow(FOLLOW_formula8_in_formula73213);
            formula8105=formula8();

            state._fsp--;

            adaptor.addChild(root_0, formula8105.getTree());

            // TcSimple.g:269:20: ( ( PLUS | MINUS ) ^ formula8 )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==MINUS||LA32_0==PLUS) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // TcSimple.g:269:21: ( PLUS | MINUS ) ^ formula8
            	    {
            	    set106=(Token)input.LT(1);

            	    set106=(Token)input.LT(1);

            	    if ( input.LA(1)==MINUS||input.LA(1)==PLUS ) {
            	        input.consume();
            	        root_0 = (TcAst)adaptor.becomeRoot(
            	        (TcAst)adaptor.create(set106)
            	        , root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_formula8_in_formula73223);
            	    formula8107=formula8();

            	    state._fsp--;

            	    adaptor.addChild(root_0, formula8107.getTree());

            	    }
            	    break;

            	default :
            	    break loop32;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "formula7"


    public static class formula8_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formula8"
    // TcSimple.g:270:1: formula8 : formula9 ( ( ASTERISK | SLASH | DIV | MOD ) ^ formula9 )* ;
    public final TcSimpleParser.formula8_return formula8() throws RecognitionException {
        TcSimpleParser.formula8_return retval = new TcSimpleParser.formula8_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token set109=null;
        TcSimpleParser.formula9_return formula9108 =null;

        TcSimpleParser.formula9_return formula9110 =null;


        TcAst set109_tree=null;

        try {
            // TcSimple.g:270:9: ( formula9 ( ( ASTERISK | SLASH | DIV | MOD ) ^ formula9 )* )
            // TcSimple.g:270:11: formula9 ( ( ASTERISK | SLASH | DIV | MOD ) ^ formula9 )*
            {
            root_0 = (TcAst)adaptor.nil();


            pushFollow(FOLLOW_formula9_in_formula83231);
            formula9108=formula9();

            state._fsp--;

            adaptor.addChild(root_0, formula9108.getTree());

            // TcSimple.g:270:20: ( ( ASTERISK | SLASH | DIV | MOD ) ^ formula9 )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==ASTERISK||LA33_0==DIV||LA33_0==MOD||LA33_0==SLASH) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // TcSimple.g:270:21: ( ASTERISK | SLASH | DIV | MOD ) ^ formula9
            	    {
            	    set109=(Token)input.LT(1);

            	    set109=(Token)input.LT(1);

            	    if ( input.LA(1)==ASTERISK||input.LA(1)==DIV||input.LA(1)==MOD||input.LA(1)==SLASH ) {
            	        input.consume();
            	        root_0 = (TcAst)adaptor.becomeRoot(
            	        (TcAst)adaptor.create(set109)
            	        , root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_formula9_in_formula83245);
            	    formula9110=formula9();

            	    state._fsp--;

            	    adaptor.addChild(root_0, formula9110.getTree());

            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "formula8"


    public static class formula9_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formula9"
    // TcSimple.g:271:1: formula9 : formula10 ( POWER ^ formula9 )? ;
    public final TcSimpleParser.formula9_return formula9() throws RecognitionException {
        TcSimpleParser.formula9_return retval = new TcSimpleParser.formula9_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token POWER112=null;
        TcSimpleParser.formula10_return formula10111 =null;

        TcSimpleParser.formula9_return formula9113 =null;


        TcAst POWER112_tree=null;

        try {
            // TcSimple.g:271:9: ( formula10 ( POWER ^ formula9 )? )
            // TcSimple.g:271:11: formula10 ( POWER ^ formula9 )?
            {
            root_0 = (TcAst)adaptor.nil();


            pushFollow(FOLLOW_formula10_in_formula93253);
            formula10111=formula10();

            state._fsp--;

            adaptor.addChild(root_0, formula10111.getTree());

            // TcSimple.g:271:21: ( POWER ^ formula9 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==POWER) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // TcSimple.g:271:22: POWER ^ formula9
                    {
                    POWER112=(Token)match(input,POWER,FOLLOW_POWER_in_formula93256); 
                    POWER112_tree = 
                    (TcAst)adaptor.create(POWER112)
                    ;
                    root_0 = (TcAst)adaptor.becomeRoot(POWER112_tree, root_0);


                    pushFollow(FOLLOW_formula9_in_formula93259);
                    formula9113=formula9();

                    state._fsp--;

                    adaptor.addChild(root_0, formula9113.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "formula9"


    public static class formula10_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "formula10"
    // TcSimple.g:272:1: formula10 : ( PLUS ^| MINUS ^)* expression ;
    public final TcSimpleParser.formula10_return formula10() throws RecognitionException {
        TcSimpleParser.formula10_return retval = new TcSimpleParser.formula10_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token PLUS114=null;
        Token MINUS115=null;
        TcSimpleParser.expression_return expression116 =null;


        TcAst PLUS114_tree=null;
        TcAst MINUS115_tree=null;

        try {
            // TcSimple.g:272:10: ( ( PLUS ^| MINUS ^)* expression )
            // TcSimple.g:272:12: ( PLUS ^| MINUS ^)* expression
            {
            root_0 = (TcAst)adaptor.nil();


            // TcSimple.g:272:12: ( PLUS ^| MINUS ^)*
            loop35:
            do {
                int alt35=3;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==PLUS) ) {
                    alt35=1;
                }
                else if ( (LA35_0==MINUS) ) {
                    alt35=2;
                }


                switch (alt35) {
            	case 1 :
            	    // TcSimple.g:272:13: PLUS ^
            	    {
            	    PLUS114=(Token)match(input,PLUS,FOLLOW_PLUS_in_formula103268); 
            	    PLUS114_tree = 
            	    (TcAst)adaptor.create(PLUS114)
            	    ;
            	    root_0 = (TcAst)adaptor.becomeRoot(PLUS114_tree, root_0);


            	    }
            	    break;
            	case 2 :
            	    // TcSimple.g:272:19: MINUS ^
            	    {
            	    MINUS115=(Token)match(input,MINUS,FOLLOW_MINUS_in_formula103271); 
            	    MINUS115_tree = 
            	    (TcAst)adaptor.create(MINUS115)
            	    ;
            	    root_0 = (TcAst)adaptor.becomeRoot(MINUS115_tree, root_0);


            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);


            pushFollow(FOLLOW_expression_in_formula103276);
            expression116=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression116.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "formula10"


    public static class expression_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "expression"
    // TcSimple.g:274:1: expression : ( PAREN_OPEN ! formula PAREN_CLOSE !| KEYWORD_SUMX PAREN_OPEN id COMMA formula COMMA formula COMMA formula PAREN_CLOSE -> ^( KEYWORD_SUMX id ( formula )* ) | KEYWORD_PRODX PAREN_OPEN id COMMA formula COMMA formula COMMA formula PAREN_CLOSE -> ^( KEYWORD_PRODX id ( formula )* ) | KEYWORD_VECTORX PAREN_OPEN id COMMA formula COMMA formula COMMA formula PAREN_CLOSE -> ^( KEYWORD_VECTORX id ( formula )* ) | KEYWORD_COLLATE PAREN_OPEN id ( PAREN_OPEN formula ( COMMA formula )* PAREN_CLOSE )? PAREN_CLOSE -> ^( KEYWORD_COLLATE id ( formula )* ) | KEYWORD_EXTRACT PAREN_OPEN id ( PAREN_OPEN formula ( COMMA formula )* PAREN_CLOSE )? COMMA formula PAREN_CLOSE -> ^( KEYWORD_EXTRACT id ( formula )* ) | KEYWORD_CELL PAREN_OPEN tableref COMMA range COMMA range PAREN_CLOSE -> ^( KEYWORD_CELL tableref ( range )* ) | KEYWORD_CELLX PAREN_OPEN tableref COMMA range COMMA range PAREN_CLOSE -> ^( KEYWORD_CELLX tableref ( range )* ) | KEYWORD_LOOKUP PAREN_OPEN tableref COMMA formula PAREN_CLOSE -> ^( KEYWORD_LOOKUP tableref ( formula )* ) | KEYWORD_LOOKUPX PAREN_OPEN tableref COMMA formula ( COMMA formula )* PAREN_CLOSE -> ^( KEYWORD_LOOKUPX tableref ( formula )* ) | KEYWORD_LOOKDOWNX PAREN_OPEN tableref COMMA formula ( COMMA formula )* PAREN_CLOSE -> ^( KEYWORD_LOOKDOWNX tableref ( formula )* ) | KEYWORD_EXISTS PAREN_OPEN tableref COMMA formula ( COMMA formula )* PAREN_CLOSE -> ^( KEYWORD_EXISTS tableref ( formula )* ) | KEYWORD_INTERPOL PAREN_OPEN tableref COMMA formula PAREN_CLOSE -> ^( KEYWORD_INTERPOL tableref formula ) | KEYWORD_TABCOLS PAREN_OPEN tableref PAREN_CLOSE -> ^( KEYWORD_TABCOLS tableref ) | KEYWORD_TABROWS PAREN_OPEN tableref PAREN_CLOSE -> ^( KEYWORD_TABROWS tableref ) | KEYWORD_FUNCREF PAREN_OPEN formula PAREN_CLOSE -> ^( KEYWORD_FUNCREF formula ) | KEYWORD_DOCALL PAREN_OPEN formula ( COMMA formula )* PAREN_CLOSE -> ^( KEYWORD_DOCALL ( formula )* ) | KEYWORD_COUNTERLIST PAREN_OPEN id ( COMMA id )* PAREN_CLOSE -> ^( KEYWORD_COUNTERLIST ( id )* ) | ID -> ^( TT_USEID ID ) | ID DOT id -> ^( TT_INPUTCALCCALLSIMPLE ID id ) | ID index ( columnaccess )? -> ^( TT_INPUTORTABACCESSWITHINDEX ID index ( columnaccess )? ) | dyntable ( index ( columnaccess )? )? -> ^( TT_DYNTABLE dyntable ( index )? ( columnaccess )? ) | ID parameterListe -> ^( TT_FUNORCALCCALL ID parameterListe ) | ifstmt | casestmt | constant );
    public final TcSimpleParser.expression_return expression() throws RecognitionException {
        TcSimpleParser.expression_return retval = new TcSimpleParser.expression_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token PAREN_OPEN117=null;
        Token PAREN_CLOSE119=null;
        Token KEYWORD_SUMX120=null;
        Token PAREN_OPEN121=null;
        Token COMMA123=null;
        Token COMMA125=null;
        Token COMMA127=null;
        Token PAREN_CLOSE129=null;
        Token KEYWORD_PRODX130=null;
        Token PAREN_OPEN131=null;
        Token COMMA133=null;
        Token COMMA135=null;
        Token COMMA137=null;
        Token PAREN_CLOSE139=null;
        Token KEYWORD_VECTORX140=null;
        Token PAREN_OPEN141=null;
        Token COMMA143=null;
        Token COMMA145=null;
        Token COMMA147=null;
        Token PAREN_CLOSE149=null;
        Token KEYWORD_COLLATE150=null;
        Token PAREN_OPEN151=null;
        Token PAREN_OPEN153=null;
        Token COMMA155=null;
        Token PAREN_CLOSE157=null;
        Token PAREN_CLOSE158=null;
        Token KEYWORD_EXTRACT159=null;
        Token PAREN_OPEN160=null;
        Token PAREN_OPEN162=null;
        Token COMMA164=null;
        Token PAREN_CLOSE166=null;
        Token COMMA167=null;
        Token PAREN_CLOSE169=null;
        Token KEYWORD_CELL170=null;
        Token PAREN_OPEN171=null;
        Token COMMA173=null;
        Token COMMA175=null;
        Token PAREN_CLOSE177=null;
        Token KEYWORD_CELLX178=null;
        Token PAREN_OPEN179=null;
        Token COMMA181=null;
        Token COMMA183=null;
        Token PAREN_CLOSE185=null;
        Token KEYWORD_LOOKUP186=null;
        Token PAREN_OPEN187=null;
        Token COMMA189=null;
        Token PAREN_CLOSE191=null;
        Token KEYWORD_LOOKUPX192=null;
        Token PAREN_OPEN193=null;
        Token COMMA195=null;
        Token COMMA197=null;
        Token PAREN_CLOSE199=null;
        Token KEYWORD_LOOKDOWNX200=null;
        Token PAREN_OPEN201=null;
        Token COMMA203=null;
        Token COMMA205=null;
        Token PAREN_CLOSE207=null;
        Token KEYWORD_EXISTS208=null;
        Token PAREN_OPEN209=null;
        Token COMMA211=null;
        Token COMMA213=null;
        Token PAREN_CLOSE215=null;
        Token KEYWORD_INTERPOL216=null;
        Token PAREN_OPEN217=null;
        Token COMMA219=null;
        Token PAREN_CLOSE221=null;
        Token KEYWORD_TABCOLS222=null;
        Token PAREN_OPEN223=null;
        Token PAREN_CLOSE225=null;
        Token KEYWORD_TABROWS226=null;
        Token PAREN_OPEN227=null;
        Token PAREN_CLOSE229=null;
        Token KEYWORD_FUNCREF230=null;
        Token PAREN_OPEN231=null;
        Token PAREN_CLOSE233=null;
        Token KEYWORD_DOCALL234=null;
        Token PAREN_OPEN235=null;
        Token COMMA237=null;
        Token PAREN_CLOSE239=null;
        Token KEYWORD_COUNTERLIST240=null;
        Token PAREN_OPEN241=null;
        Token COMMA243=null;
        Token PAREN_CLOSE245=null;
        Token ID246=null;
        Token ID247=null;
        Token DOT248=null;
        Token ID250=null;
        Token ID256=null;
        TcSimpleParser.formula_return formula118 =null;

        TcSimpleParser.id_return id122 =null;

        TcSimpleParser.formula_return formula124 =null;

        TcSimpleParser.formula_return formula126 =null;

        TcSimpleParser.formula_return formula128 =null;

        TcSimpleParser.id_return id132 =null;

        TcSimpleParser.formula_return formula134 =null;

        TcSimpleParser.formula_return formula136 =null;

        TcSimpleParser.formula_return formula138 =null;

        TcSimpleParser.id_return id142 =null;

        TcSimpleParser.formula_return formula144 =null;

        TcSimpleParser.formula_return formula146 =null;

        TcSimpleParser.formula_return formula148 =null;

        TcSimpleParser.id_return id152 =null;

        TcSimpleParser.formula_return formula154 =null;

        TcSimpleParser.formula_return formula156 =null;

        TcSimpleParser.id_return id161 =null;

        TcSimpleParser.formula_return formula163 =null;

        TcSimpleParser.formula_return formula165 =null;

        TcSimpleParser.formula_return formula168 =null;

        TcSimpleParser.tableref_return tableref172 =null;

        TcSimpleParser.range_return range174 =null;

        TcSimpleParser.range_return range176 =null;

        TcSimpleParser.tableref_return tableref180 =null;

        TcSimpleParser.range_return range182 =null;

        TcSimpleParser.range_return range184 =null;

        TcSimpleParser.tableref_return tableref188 =null;

        TcSimpleParser.formula_return formula190 =null;

        TcSimpleParser.tableref_return tableref194 =null;

        TcSimpleParser.formula_return formula196 =null;

        TcSimpleParser.formula_return formula198 =null;

        TcSimpleParser.tableref_return tableref202 =null;

        TcSimpleParser.formula_return formula204 =null;

        TcSimpleParser.formula_return formula206 =null;

        TcSimpleParser.tableref_return tableref210 =null;

        TcSimpleParser.formula_return formula212 =null;

        TcSimpleParser.formula_return formula214 =null;

        TcSimpleParser.tableref_return tableref218 =null;

        TcSimpleParser.formula_return formula220 =null;

        TcSimpleParser.tableref_return tableref224 =null;

        TcSimpleParser.tableref_return tableref228 =null;

        TcSimpleParser.formula_return formula232 =null;

        TcSimpleParser.formula_return formula236 =null;

        TcSimpleParser.formula_return formula238 =null;

        TcSimpleParser.id_return id242 =null;

        TcSimpleParser.id_return id244 =null;

        TcSimpleParser.id_return id249 =null;

        TcSimpleParser.index_return index251 =null;

        TcSimpleParser.columnaccess_return columnaccess252 =null;

        TcSimpleParser.dyntable_return dyntable253 =null;

        TcSimpleParser.index_return index254 =null;

        TcSimpleParser.columnaccess_return columnaccess255 =null;

        TcSimpleParser.parameterListe_return parameterListe257 =null;

        TcSimpleParser.ifstmt_return ifstmt258 =null;

        TcSimpleParser.casestmt_return casestmt259 =null;

        TcSimpleParser.constant_return constant260 =null;


        TcAst PAREN_OPEN117_tree=null;
        TcAst PAREN_CLOSE119_tree=null;
        TcAst KEYWORD_SUMX120_tree=null;
        TcAst PAREN_OPEN121_tree=null;
        TcAst COMMA123_tree=null;
        TcAst COMMA125_tree=null;
        TcAst COMMA127_tree=null;
        TcAst PAREN_CLOSE129_tree=null;
        TcAst KEYWORD_PRODX130_tree=null;
        TcAst PAREN_OPEN131_tree=null;
        TcAst COMMA133_tree=null;
        TcAst COMMA135_tree=null;
        TcAst COMMA137_tree=null;
        TcAst PAREN_CLOSE139_tree=null;
        TcAst KEYWORD_VECTORX140_tree=null;
        TcAst PAREN_OPEN141_tree=null;
        TcAst COMMA143_tree=null;
        TcAst COMMA145_tree=null;
        TcAst COMMA147_tree=null;
        TcAst PAREN_CLOSE149_tree=null;
        TcAst KEYWORD_COLLATE150_tree=null;
        TcAst PAREN_OPEN151_tree=null;
        TcAst PAREN_OPEN153_tree=null;
        TcAst COMMA155_tree=null;
        TcAst PAREN_CLOSE157_tree=null;
        TcAst PAREN_CLOSE158_tree=null;
        TcAst KEYWORD_EXTRACT159_tree=null;
        TcAst PAREN_OPEN160_tree=null;
        TcAst PAREN_OPEN162_tree=null;
        TcAst COMMA164_tree=null;
        TcAst PAREN_CLOSE166_tree=null;
        TcAst COMMA167_tree=null;
        TcAst PAREN_CLOSE169_tree=null;
        TcAst KEYWORD_CELL170_tree=null;
        TcAst PAREN_OPEN171_tree=null;
        TcAst COMMA173_tree=null;
        TcAst COMMA175_tree=null;
        TcAst PAREN_CLOSE177_tree=null;
        TcAst KEYWORD_CELLX178_tree=null;
        TcAst PAREN_OPEN179_tree=null;
        TcAst COMMA181_tree=null;
        TcAst COMMA183_tree=null;
        TcAst PAREN_CLOSE185_tree=null;
        TcAst KEYWORD_LOOKUP186_tree=null;
        TcAst PAREN_OPEN187_tree=null;
        TcAst COMMA189_tree=null;
        TcAst PAREN_CLOSE191_tree=null;
        TcAst KEYWORD_LOOKUPX192_tree=null;
        TcAst PAREN_OPEN193_tree=null;
        TcAst COMMA195_tree=null;
        TcAst COMMA197_tree=null;
        TcAst PAREN_CLOSE199_tree=null;
        TcAst KEYWORD_LOOKDOWNX200_tree=null;
        TcAst PAREN_OPEN201_tree=null;
        TcAst COMMA203_tree=null;
        TcAst COMMA205_tree=null;
        TcAst PAREN_CLOSE207_tree=null;
        TcAst KEYWORD_EXISTS208_tree=null;
        TcAst PAREN_OPEN209_tree=null;
        TcAst COMMA211_tree=null;
        TcAst COMMA213_tree=null;
        TcAst PAREN_CLOSE215_tree=null;
        TcAst KEYWORD_INTERPOL216_tree=null;
        TcAst PAREN_OPEN217_tree=null;
        TcAst COMMA219_tree=null;
        TcAst PAREN_CLOSE221_tree=null;
        TcAst KEYWORD_TABCOLS222_tree=null;
        TcAst PAREN_OPEN223_tree=null;
        TcAst PAREN_CLOSE225_tree=null;
        TcAst KEYWORD_TABROWS226_tree=null;
        TcAst PAREN_OPEN227_tree=null;
        TcAst PAREN_CLOSE229_tree=null;
        TcAst KEYWORD_FUNCREF230_tree=null;
        TcAst PAREN_OPEN231_tree=null;
        TcAst PAREN_CLOSE233_tree=null;
        TcAst KEYWORD_DOCALL234_tree=null;
        TcAst PAREN_OPEN235_tree=null;
        TcAst COMMA237_tree=null;
        TcAst PAREN_CLOSE239_tree=null;
        TcAst KEYWORD_COUNTERLIST240_tree=null;
        TcAst PAREN_OPEN241_tree=null;
        TcAst COMMA243_tree=null;
        TcAst PAREN_CLOSE245_tree=null;
        TcAst ID246_tree=null;
        TcAst ID247_tree=null;
        TcAst DOT248_tree=null;
        TcAst ID250_tree=null;
        TcAst ID256_tree=null;
        RewriteRuleTokenStream stream_KEYWORD_TABCOLS=new RewriteRuleTokenStream(adaptor,"token KEYWORD_TABCOLS");
        RewriteRuleTokenStream stream_KEYWORD_EXISTS=new RewriteRuleTokenStream(adaptor,"token KEYWORD_EXISTS");
        RewriteRuleTokenStream stream_KEYWORD_COUNTERLIST=new RewriteRuleTokenStream(adaptor,"token KEYWORD_COUNTERLIST");
        RewriteRuleTokenStream stream_KEYWORD_CELL=new RewriteRuleTokenStream(adaptor,"token KEYWORD_CELL");
        RewriteRuleTokenStream stream_KEYWORD_TABROWS=new RewriteRuleTokenStream(adaptor,"token KEYWORD_TABROWS");
        RewriteRuleTokenStream stream_KEYWORD_SUMX=new RewriteRuleTokenStream(adaptor,"token KEYWORD_SUMX");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_KEYWORD_LOOKUPX=new RewriteRuleTokenStream(adaptor,"token KEYWORD_LOOKUPX");
        RewriteRuleTokenStream stream_KEYWORD_VECTORX=new RewriteRuleTokenStream(adaptor,"token KEYWORD_VECTORX");
        RewriteRuleTokenStream stream_PAREN_CLOSE=new RewriteRuleTokenStream(adaptor,"token PAREN_CLOSE");
        RewriteRuleTokenStream stream_KEYWORD_DOCALL=new RewriteRuleTokenStream(adaptor,"token KEYWORD_DOCALL");
        RewriteRuleTokenStream stream_KEYWORD_EXTRACT=new RewriteRuleTokenStream(adaptor,"token KEYWORD_EXTRACT");
        RewriteRuleTokenStream stream_KEYWORD_FUNCREF=new RewriteRuleTokenStream(adaptor,"token KEYWORD_FUNCREF");
        RewriteRuleTokenStream stream_KEYWORD_CELLX=new RewriteRuleTokenStream(adaptor,"token KEYWORD_CELLX");
        RewriteRuleTokenStream stream_KEYWORD_INTERPOL=new RewriteRuleTokenStream(adaptor,"token KEYWORD_INTERPOL");
        RewriteRuleTokenStream stream_KEYWORD_LOOKDOWNX=new RewriteRuleTokenStream(adaptor,"token KEYWORD_LOOKDOWNX");
        RewriteRuleTokenStream stream_PAREN_OPEN=new RewriteRuleTokenStream(adaptor,"token PAREN_OPEN");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_KEYWORD_COLLATE=new RewriteRuleTokenStream(adaptor,"token KEYWORD_COLLATE");
        RewriteRuleTokenStream stream_KEYWORD_PRODX=new RewriteRuleTokenStream(adaptor,"token KEYWORD_PRODX");
        RewriteRuleTokenStream stream_KEYWORD_LOOKUP=new RewriteRuleTokenStream(adaptor,"token KEYWORD_LOOKUP");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_index=new RewriteRuleSubtreeStream(adaptor,"rule index");
        RewriteRuleSubtreeStream stream_range=new RewriteRuleSubtreeStream(adaptor,"rule range");
        RewriteRuleSubtreeStream stream_dyntable=new RewriteRuleSubtreeStream(adaptor,"rule dyntable");
        RewriteRuleSubtreeStream stream_tableref=new RewriteRuleSubtreeStream(adaptor,"rule tableref");
        RewriteRuleSubtreeStream stream_parameterListe=new RewriteRuleSubtreeStream(adaptor,"rule parameterListe");
        RewriteRuleSubtreeStream stream_columnaccess=new RewriteRuleSubtreeStream(adaptor,"rule columnaccess");
        RewriteRuleSubtreeStream stream_formula=new RewriteRuleSubtreeStream(adaptor,"rule formula");
        try {
            // TcSimple.g:274:11: ( PAREN_OPEN ! formula PAREN_CLOSE !| KEYWORD_SUMX PAREN_OPEN id COMMA formula COMMA formula COMMA formula PAREN_CLOSE -> ^( KEYWORD_SUMX id ( formula )* ) | KEYWORD_PRODX PAREN_OPEN id COMMA formula COMMA formula COMMA formula PAREN_CLOSE -> ^( KEYWORD_PRODX id ( formula )* ) | KEYWORD_VECTORX PAREN_OPEN id COMMA formula COMMA formula COMMA formula PAREN_CLOSE -> ^( KEYWORD_VECTORX id ( formula )* ) | KEYWORD_COLLATE PAREN_OPEN id ( PAREN_OPEN formula ( COMMA formula )* PAREN_CLOSE )? PAREN_CLOSE -> ^( KEYWORD_COLLATE id ( formula )* ) | KEYWORD_EXTRACT PAREN_OPEN id ( PAREN_OPEN formula ( COMMA formula )* PAREN_CLOSE )? COMMA formula PAREN_CLOSE -> ^( KEYWORD_EXTRACT id ( formula )* ) | KEYWORD_CELL PAREN_OPEN tableref COMMA range COMMA range PAREN_CLOSE -> ^( KEYWORD_CELL tableref ( range )* ) | KEYWORD_CELLX PAREN_OPEN tableref COMMA range COMMA range PAREN_CLOSE -> ^( KEYWORD_CELLX tableref ( range )* ) | KEYWORD_LOOKUP PAREN_OPEN tableref COMMA formula PAREN_CLOSE -> ^( KEYWORD_LOOKUP tableref ( formula )* ) | KEYWORD_LOOKUPX PAREN_OPEN tableref COMMA formula ( COMMA formula )* PAREN_CLOSE -> ^( KEYWORD_LOOKUPX tableref ( formula )* ) | KEYWORD_LOOKDOWNX PAREN_OPEN tableref COMMA formula ( COMMA formula )* PAREN_CLOSE -> ^( KEYWORD_LOOKDOWNX tableref ( formula )* ) | KEYWORD_EXISTS PAREN_OPEN tableref COMMA formula ( COMMA formula )* PAREN_CLOSE -> ^( KEYWORD_EXISTS tableref ( formula )* ) | KEYWORD_INTERPOL PAREN_OPEN tableref COMMA formula PAREN_CLOSE -> ^( KEYWORD_INTERPOL tableref formula ) | KEYWORD_TABCOLS PAREN_OPEN tableref PAREN_CLOSE -> ^( KEYWORD_TABCOLS tableref ) | KEYWORD_TABROWS PAREN_OPEN tableref PAREN_CLOSE -> ^( KEYWORD_TABROWS tableref ) | KEYWORD_FUNCREF PAREN_OPEN formula PAREN_CLOSE -> ^( KEYWORD_FUNCREF formula ) | KEYWORD_DOCALL PAREN_OPEN formula ( COMMA formula )* PAREN_CLOSE -> ^( KEYWORD_DOCALL ( formula )* ) | KEYWORD_COUNTERLIST PAREN_OPEN id ( COMMA id )* PAREN_CLOSE -> ^( KEYWORD_COUNTERLIST ( id )* ) | ID -> ^( TT_USEID ID ) | ID DOT id -> ^( TT_INPUTCALCCALLSIMPLE ID id ) | ID index ( columnaccess )? -> ^( TT_INPUTORTABACCESSWITHINDEX ID index ( columnaccess )? ) | dyntable ( index ( columnaccess )? )? -> ^( TT_DYNTABLE dyntable ( index )? ( columnaccess )? ) | ID parameterListe -> ^( TT_FUNORCALCCALL ID parameterListe ) | ifstmt | casestmt | constant )
            int alt48=26;
            switch ( input.LA(1) ) {
            case PAREN_OPEN:
                {
                alt48=1;
                }
                break;
            case KEYWORD_SUMX:
                {
                alt48=2;
                }
                break;
            case KEYWORD_PRODX:
                {
                alt48=3;
                }
                break;
            case KEYWORD_VECTORX:
                {
                alt48=4;
                }
                break;
            case KEYWORD_COLLATE:
                {
                alt48=5;
                }
                break;
            case KEYWORD_EXTRACT:
                {
                alt48=6;
                }
                break;
            case KEYWORD_CELL:
                {
                alt48=7;
                }
                break;
            case KEYWORD_CELLX:
                {
                alt48=8;
                }
                break;
            case KEYWORD_LOOKUP:
                {
                alt48=9;
                }
                break;
            case KEYWORD_LOOKUPX:
                {
                alt48=10;
                }
                break;
            case KEYWORD_LOOKDOWNX:
                {
                alt48=11;
                }
                break;
            case KEYWORD_EXISTS:
                {
                alt48=12;
                }
                break;
            case KEYWORD_INTERPOL:
                {
                alt48=13;
                }
                break;
            case KEYWORD_TABCOLS:
                {
                alt48=14;
                }
                break;
            case KEYWORD_TABROWS:
                {
                alt48=15;
                }
                break;
            case KEYWORD_FUNCREF:
                {
                alt48=16;
                }
                break;
            case KEYWORD_DOCALL:
                {
                alt48=17;
                }
                break;
            case KEYWORD_COUNTERLIST:
                {
                alt48=18;
                }
                break;
            case ID:
                {
                switch ( input.LA(2) ) {
                case DOT:
                    {
                    alt48=20;
                    }
                    break;
                case EOF:
                case ASTERISK:
                case BRACKET_CLOSE:
                case COLON:
                case COMMA:
                case COMPARE_BIGGER:
                case COMPARE_BIGGEREQUAL:
                case COMPARE_EQUAL:
                case COMPARE_EQUAL_CSTYLE:
                case COMPARE_LESSEQUAL:
                case COMPARE_NOTEQUAL:
                case COMPARE_NOTEQUAL_CSTYLE:
                case COMPARE_SMALLER:
                case COMPARE_STR_AFTER:
                case COMPARE_STR_AHEAD:
                case COMPARE_STR_ALIKE:
                case COMPARE_STR_BEFORE:
                case COMPARE_STR_BEHIND:
                case COMPARE_STR_EQUAL:
                case COMPARE_STR_NOTAFTER:
                case COMPARE_STR_NOTAHEAD:
                case COMPARE_STR_NOTALIKE:
                case COMPARE_STR_NOTBEFORE:
                case COMPARE_STR_NOTBEHIND:
                case COMPARE_STR_NOTEQUAL:
                case CURLY_OPEN:
                case DIV:
                case DOTS:
                case KEYWORD_AS:
                case KEYWORD_DEFAULT:
                case KEYWORD_ELSE:
                case KEYWORD_ENDCASE:
                case KEYWORD_ENDIF:
                case KEYWORD_THEN:
                case KEYWORD_TIMES:
                case KEYWORD_WHEN:
                case LOGICAL_AND:
                case LOGICAL_OR:
                case LOGICAL_XOR:
                case MINUS:
                case MOD:
                case PAREN_CLOSE:
                case PLUS:
                case POWER:
                case QUESTIONMARK:
                case SEMICOLON:
                case SLASH:
                case STRCAT:
                    {
                    alt48=19;
                    }
                    break;
                case BRACKET_OPEN:
                    {
                    alt48=21;
                    }
                    break;
                case PAREN_OPEN:
                    {
                    alt48=23;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 48, 19, input);

                    throw nvae;

                }

                }
                break;
            case KEYWORD_TABREF:
                {
                alt48=22;
                }
                break;
            case KEYWORD_IF:
                {
                alt48=24;
                }
                break;
            case KEYWORD_CASE:
                {
                alt48=25;
                }
                break;
            case NUMBER:
            case STRING:
                {
                alt48=26;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;

            }

            switch (alt48) {
                case 1 :
                    // TcSimple.g:275:3: PAREN_OPEN ! formula PAREN_CLOSE !
                    {
                    root_0 = (TcAst)adaptor.nil();


                    PAREN_OPEN117=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3285); 

                    pushFollow(FOLLOW_formula_in_expression3288);
                    formula118=formula();

                    state._fsp--;

                    adaptor.addChild(root_0, formula118.getTree());

                    PAREN_CLOSE119=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3290); 

                    }
                    break;
                case 2 :
                    // TcSimple.g:276:3: KEYWORD_SUMX PAREN_OPEN id COMMA formula COMMA formula COMMA formula PAREN_CLOSE
                    {
                    KEYWORD_SUMX120=(Token)match(input,KEYWORD_SUMX,FOLLOW_KEYWORD_SUMX_in_expression3295);  
                    stream_KEYWORD_SUMX.add(KEYWORD_SUMX120);


                    PAREN_OPEN121=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3298);  
                    stream_PAREN_OPEN.add(PAREN_OPEN121);


                    pushFollow(FOLLOW_id_in_expression3300);
                    id122=id();

                    state._fsp--;

                    stream_id.add(id122.getTree());

                    COMMA123=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3302);  
                    stream_COMMA.add(COMMA123);


                    pushFollow(FOLLOW_formula_in_expression3304);
                    formula124=formula();

                    state._fsp--;

                    stream_formula.add(formula124.getTree());

                    COMMA125=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3306);  
                    stream_COMMA.add(COMMA125);


                    pushFollow(FOLLOW_formula_in_expression3308);
                    formula126=formula();

                    state._fsp--;

                    stream_formula.add(formula126.getTree());

                    COMMA127=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3310);  
                    stream_COMMA.add(COMMA127);


                    pushFollow(FOLLOW_formula_in_expression3312);
                    formula128=formula();

                    state._fsp--;

                    stream_formula.add(formula128.getTree());

                    PAREN_CLOSE129=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3314);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE129);


                    // AST REWRITE
                    // elements: KEYWORD_SUMX, id, formula
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 277:11: -> ^( KEYWORD_SUMX id ( formula )* )
                    {
                        // TcSimple.g:277:14: ^( KEYWORD_SUMX id ( formula )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_SUMX.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_id.nextTree());

                        // TcSimple.g:277:32: ( formula )*
                        while ( stream_formula.hasNext() ) {
                            adaptor.addChild(root_1, stream_formula.nextTree());

                        }
                        stream_formula.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 3 :
                    // TcSimple.g:278:3: KEYWORD_PRODX PAREN_OPEN id COMMA formula COMMA formula COMMA formula PAREN_CLOSE
                    {
                    KEYWORD_PRODX130=(Token)match(input,KEYWORD_PRODX,FOLLOW_KEYWORD_PRODX_in_expression3339);  
                    stream_KEYWORD_PRODX.add(KEYWORD_PRODX130);


                    PAREN_OPEN131=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3341);  
                    stream_PAREN_OPEN.add(PAREN_OPEN131);


                    pushFollow(FOLLOW_id_in_expression3343);
                    id132=id();

                    state._fsp--;

                    stream_id.add(id132.getTree());

                    COMMA133=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3345);  
                    stream_COMMA.add(COMMA133);


                    pushFollow(FOLLOW_formula_in_expression3347);
                    formula134=formula();

                    state._fsp--;

                    stream_formula.add(formula134.getTree());

                    COMMA135=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3349);  
                    stream_COMMA.add(COMMA135);


                    pushFollow(FOLLOW_formula_in_expression3351);
                    formula136=formula();

                    state._fsp--;

                    stream_formula.add(formula136.getTree());

                    COMMA137=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3353);  
                    stream_COMMA.add(COMMA137);


                    pushFollow(FOLLOW_formula_in_expression3355);
                    formula138=formula();

                    state._fsp--;

                    stream_formula.add(formula138.getTree());

                    PAREN_CLOSE139=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3357);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE139);


                    // AST REWRITE
                    // elements: formula, KEYWORD_PRODX, id
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 279:11: -> ^( KEYWORD_PRODX id ( formula )* )
                    {
                        // TcSimple.g:279:14: ^( KEYWORD_PRODX id ( formula )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_PRODX.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_id.nextTree());

                        // TcSimple.g:279:33: ( formula )*
                        while ( stream_formula.hasNext() ) {
                            adaptor.addChild(root_1, stream_formula.nextTree());

                        }
                        stream_formula.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 4 :
                    // TcSimple.g:280:3: KEYWORD_VECTORX PAREN_OPEN id COMMA formula COMMA formula COMMA formula PAREN_CLOSE
                    {
                    KEYWORD_VECTORX140=(Token)match(input,KEYWORD_VECTORX,FOLLOW_KEYWORD_VECTORX_in_expression3382);  
                    stream_KEYWORD_VECTORX.add(KEYWORD_VECTORX140);


                    PAREN_OPEN141=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3384);  
                    stream_PAREN_OPEN.add(PAREN_OPEN141);


                    pushFollow(FOLLOW_id_in_expression3386);
                    id142=id();

                    state._fsp--;

                    stream_id.add(id142.getTree());

                    COMMA143=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3388);  
                    stream_COMMA.add(COMMA143);


                    pushFollow(FOLLOW_formula_in_expression3390);
                    formula144=formula();

                    state._fsp--;

                    stream_formula.add(formula144.getTree());

                    COMMA145=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3392);  
                    stream_COMMA.add(COMMA145);


                    pushFollow(FOLLOW_formula_in_expression3394);
                    formula146=formula();

                    state._fsp--;

                    stream_formula.add(formula146.getTree());

                    COMMA147=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3396);  
                    stream_COMMA.add(COMMA147);


                    pushFollow(FOLLOW_formula_in_expression3398);
                    formula148=formula();

                    state._fsp--;

                    stream_formula.add(formula148.getTree());

                    PAREN_CLOSE149=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3400);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE149);


                    // AST REWRITE
                    // elements: formula, id, KEYWORD_VECTORX
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 281:11: -> ^( KEYWORD_VECTORX id ( formula )* )
                    {
                        // TcSimple.g:281:14: ^( KEYWORD_VECTORX id ( formula )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_VECTORX.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_id.nextTree());

                        // TcSimple.g:281:35: ( formula )*
                        while ( stream_formula.hasNext() ) {
                            adaptor.addChild(root_1, stream_formula.nextTree());

                        }
                        stream_formula.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 5 :
                    // TcSimple.g:282:3: KEYWORD_COLLATE PAREN_OPEN id ( PAREN_OPEN formula ( COMMA formula )* PAREN_CLOSE )? PAREN_CLOSE
                    {
                    KEYWORD_COLLATE150=(Token)match(input,KEYWORD_COLLATE,FOLLOW_KEYWORD_COLLATE_in_expression3425);  
                    stream_KEYWORD_COLLATE.add(KEYWORD_COLLATE150);


                    PAREN_OPEN151=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3427);  
                    stream_PAREN_OPEN.add(PAREN_OPEN151);


                    pushFollow(FOLLOW_id_in_expression3429);
                    id152=id();

                    state._fsp--;

                    stream_id.add(id152.getTree());

                    // TcSimple.g:282:33: ( PAREN_OPEN formula ( COMMA formula )* PAREN_CLOSE )?
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==PAREN_OPEN) ) {
                        alt37=1;
                    }
                    switch (alt37) {
                        case 1 :
                            // TcSimple.g:282:34: PAREN_OPEN formula ( COMMA formula )* PAREN_CLOSE
                            {
                            PAREN_OPEN153=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3432);  
                            stream_PAREN_OPEN.add(PAREN_OPEN153);


                            pushFollow(FOLLOW_formula_in_expression3434);
                            formula154=formula();

                            state._fsp--;

                            stream_formula.add(formula154.getTree());

                            // TcSimple.g:282:53: ( COMMA formula )*
                            loop36:
                            do {
                                int alt36=2;
                                int LA36_0 = input.LA(1);

                                if ( (LA36_0==COMMA) ) {
                                    alt36=1;
                                }


                                switch (alt36) {
                            	case 1 :
                            	    // TcSimple.g:282:54: COMMA formula
                            	    {
                            	    COMMA155=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3437);  
                            	    stream_COMMA.add(COMMA155);


                            	    pushFollow(FOLLOW_formula_in_expression3439);
                            	    formula156=formula();

                            	    state._fsp--;

                            	    stream_formula.add(formula156.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop36;
                                }
                            } while (true);


                            PAREN_CLOSE157=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3443);  
                            stream_PAREN_CLOSE.add(PAREN_CLOSE157);


                            }
                            break;

                    }


                    PAREN_CLOSE158=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3447);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE158);


                    // AST REWRITE
                    // elements: KEYWORD_COLLATE, id, formula
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 283:11: -> ^( KEYWORD_COLLATE id ( formula )* )
                    {
                        // TcSimple.g:283:14: ^( KEYWORD_COLLATE id ( formula )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_COLLATE.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_id.nextTree());

                        // TcSimple.g:283:35: ( formula )*
                        while ( stream_formula.hasNext() ) {
                            adaptor.addChild(root_1, stream_formula.nextTree());

                        }
                        stream_formula.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 6 :
                    // TcSimple.g:284:3: KEYWORD_EXTRACT PAREN_OPEN id ( PAREN_OPEN formula ( COMMA formula )* PAREN_CLOSE )? COMMA formula PAREN_CLOSE
                    {
                    KEYWORD_EXTRACT159=(Token)match(input,KEYWORD_EXTRACT,FOLLOW_KEYWORD_EXTRACT_in_expression3472);  
                    stream_KEYWORD_EXTRACT.add(KEYWORD_EXTRACT159);


                    PAREN_OPEN160=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3474);  
                    stream_PAREN_OPEN.add(PAREN_OPEN160);


                    pushFollow(FOLLOW_id_in_expression3476);
                    id161=id();

                    state._fsp--;

                    stream_id.add(id161.getTree());

                    // TcSimple.g:284:33: ( PAREN_OPEN formula ( COMMA formula )* PAREN_CLOSE )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==PAREN_OPEN) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // TcSimple.g:284:34: PAREN_OPEN formula ( COMMA formula )* PAREN_CLOSE
                            {
                            PAREN_OPEN162=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3479);  
                            stream_PAREN_OPEN.add(PAREN_OPEN162);


                            pushFollow(FOLLOW_formula_in_expression3481);
                            formula163=formula();

                            state._fsp--;

                            stream_formula.add(formula163.getTree());

                            // TcSimple.g:284:53: ( COMMA formula )*
                            loop38:
                            do {
                                int alt38=2;
                                int LA38_0 = input.LA(1);

                                if ( (LA38_0==COMMA) ) {
                                    alt38=1;
                                }


                                switch (alt38) {
                            	case 1 :
                            	    // TcSimple.g:284:54: COMMA formula
                            	    {
                            	    COMMA164=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3484);  
                            	    stream_COMMA.add(COMMA164);


                            	    pushFollow(FOLLOW_formula_in_expression3486);
                            	    formula165=formula();

                            	    state._fsp--;

                            	    stream_formula.add(formula165.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop38;
                                }
                            } while (true);


                            PAREN_CLOSE166=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3490);  
                            stream_PAREN_CLOSE.add(PAREN_CLOSE166);


                            }
                            break;

                    }


                    COMMA167=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3494);  
                    stream_COMMA.add(COMMA167);


                    pushFollow(FOLLOW_formula_in_expression3496);
                    formula168=formula();

                    state._fsp--;

                    stream_formula.add(formula168.getTree());

                    PAREN_CLOSE169=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3498);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE169);


                    // AST REWRITE
                    // elements: KEYWORD_EXTRACT, formula, id
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 285:11: -> ^( KEYWORD_EXTRACT id ( formula )* )
                    {
                        // TcSimple.g:285:14: ^( KEYWORD_EXTRACT id ( formula )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_EXTRACT.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_id.nextTree());

                        // TcSimple.g:285:35: ( formula )*
                        while ( stream_formula.hasNext() ) {
                            adaptor.addChild(root_1, stream_formula.nextTree());

                        }
                        stream_formula.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 7 :
                    // TcSimple.g:286:3: KEYWORD_CELL PAREN_OPEN tableref COMMA range COMMA range PAREN_CLOSE
                    {
                    KEYWORD_CELL170=(Token)match(input,KEYWORD_CELL,FOLLOW_KEYWORD_CELL_in_expression3523);  
                    stream_KEYWORD_CELL.add(KEYWORD_CELL170);


                    PAREN_OPEN171=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3525);  
                    stream_PAREN_OPEN.add(PAREN_OPEN171);


                    pushFollow(FOLLOW_tableref_in_expression3527);
                    tableref172=tableref();

                    state._fsp--;

                    stream_tableref.add(tableref172.getTree());

                    COMMA173=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3529);  
                    stream_COMMA.add(COMMA173);


                    pushFollow(FOLLOW_range_in_expression3531);
                    range174=range();

                    state._fsp--;

                    stream_range.add(range174.getTree());

                    COMMA175=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3533);  
                    stream_COMMA.add(COMMA175);


                    pushFollow(FOLLOW_range_in_expression3535);
                    range176=range();

                    state._fsp--;

                    stream_range.add(range176.getTree());

                    PAREN_CLOSE177=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3537);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE177);


                    // AST REWRITE
                    // elements: range, tableref, KEYWORD_CELL
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 287:11: -> ^( KEYWORD_CELL tableref ( range )* )
                    {
                        // TcSimple.g:287:14: ^( KEYWORD_CELL tableref ( range )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_CELL.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_tableref.nextTree());

                        // TcSimple.g:287:38: ( range )*
                        while ( stream_range.hasNext() ) {
                            adaptor.addChild(root_1, stream_range.nextTree());

                        }
                        stream_range.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 8 :
                    // TcSimple.g:288:3: KEYWORD_CELLX PAREN_OPEN tableref COMMA range COMMA range PAREN_CLOSE
                    {
                    KEYWORD_CELLX178=(Token)match(input,KEYWORD_CELLX,FOLLOW_KEYWORD_CELLX_in_expression3562);  
                    stream_KEYWORD_CELLX.add(KEYWORD_CELLX178);


                    PAREN_OPEN179=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3564);  
                    stream_PAREN_OPEN.add(PAREN_OPEN179);


                    pushFollow(FOLLOW_tableref_in_expression3566);
                    tableref180=tableref();

                    state._fsp--;

                    stream_tableref.add(tableref180.getTree());

                    COMMA181=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3568);  
                    stream_COMMA.add(COMMA181);


                    pushFollow(FOLLOW_range_in_expression3570);
                    range182=range();

                    state._fsp--;

                    stream_range.add(range182.getTree());

                    COMMA183=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3572);  
                    stream_COMMA.add(COMMA183);


                    pushFollow(FOLLOW_range_in_expression3574);
                    range184=range();

                    state._fsp--;

                    stream_range.add(range184.getTree());

                    PAREN_CLOSE185=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3576);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE185);


                    // AST REWRITE
                    // elements: tableref, range, KEYWORD_CELLX
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 289:11: -> ^( KEYWORD_CELLX tableref ( range )* )
                    {
                        // TcSimple.g:289:14: ^( KEYWORD_CELLX tableref ( range )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_CELLX.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_tableref.nextTree());

                        // TcSimple.g:289:39: ( range )*
                        while ( stream_range.hasNext() ) {
                            adaptor.addChild(root_1, stream_range.nextTree());

                        }
                        stream_range.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 9 :
                    // TcSimple.g:290:3: KEYWORD_LOOKUP PAREN_OPEN tableref COMMA formula PAREN_CLOSE
                    {
                    KEYWORD_LOOKUP186=(Token)match(input,KEYWORD_LOOKUP,FOLLOW_KEYWORD_LOOKUP_in_expression3601);  
                    stream_KEYWORD_LOOKUP.add(KEYWORD_LOOKUP186);


                    PAREN_OPEN187=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3603);  
                    stream_PAREN_OPEN.add(PAREN_OPEN187);


                    pushFollow(FOLLOW_tableref_in_expression3605);
                    tableref188=tableref();

                    state._fsp--;

                    stream_tableref.add(tableref188.getTree());

                    COMMA189=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3607);  
                    stream_COMMA.add(COMMA189);


                    pushFollow(FOLLOW_formula_in_expression3609);
                    formula190=formula();

                    state._fsp--;

                    stream_formula.add(formula190.getTree());

                    PAREN_CLOSE191=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3611);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE191);


                    // AST REWRITE
                    // elements: KEYWORD_LOOKUP, formula, tableref
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 291:11: -> ^( KEYWORD_LOOKUP tableref ( formula )* )
                    {
                        // TcSimple.g:291:14: ^( KEYWORD_LOOKUP tableref ( formula )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_LOOKUP.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_tableref.nextTree());

                        // TcSimple.g:291:40: ( formula )*
                        while ( stream_formula.hasNext() ) {
                            adaptor.addChild(root_1, stream_formula.nextTree());

                        }
                        stream_formula.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 10 :
                    // TcSimple.g:292:3: KEYWORD_LOOKUPX PAREN_OPEN tableref COMMA formula ( COMMA formula )* PAREN_CLOSE
                    {
                    KEYWORD_LOOKUPX192=(Token)match(input,KEYWORD_LOOKUPX,FOLLOW_KEYWORD_LOOKUPX_in_expression3636);  
                    stream_KEYWORD_LOOKUPX.add(KEYWORD_LOOKUPX192);


                    PAREN_OPEN193=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3638);  
                    stream_PAREN_OPEN.add(PAREN_OPEN193);


                    pushFollow(FOLLOW_tableref_in_expression3640);
                    tableref194=tableref();

                    state._fsp--;

                    stream_tableref.add(tableref194.getTree());

                    COMMA195=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3642);  
                    stream_COMMA.add(COMMA195);


                    pushFollow(FOLLOW_formula_in_expression3644);
                    formula196=formula();

                    state._fsp--;

                    stream_formula.add(formula196.getTree());

                    // TcSimple.g:292:53: ( COMMA formula )*
                    loop40:
                    do {
                        int alt40=2;
                        int LA40_0 = input.LA(1);

                        if ( (LA40_0==COMMA) ) {
                            alt40=1;
                        }


                        switch (alt40) {
                    	case 1 :
                    	    // TcSimple.g:292:54: COMMA formula
                    	    {
                    	    COMMA197=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3647);  
                    	    stream_COMMA.add(COMMA197);


                    	    pushFollow(FOLLOW_formula_in_expression3649);
                    	    formula198=formula();

                    	    state._fsp--;

                    	    stream_formula.add(formula198.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop40;
                        }
                    } while (true);


                    PAREN_CLOSE199=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3653);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE199);


                    // AST REWRITE
                    // elements: tableref, KEYWORD_LOOKUPX, formula
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 293:11: -> ^( KEYWORD_LOOKUPX tableref ( formula )* )
                    {
                        // TcSimple.g:293:14: ^( KEYWORD_LOOKUPX tableref ( formula )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_LOOKUPX.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_tableref.nextTree());

                        // TcSimple.g:293:41: ( formula )*
                        while ( stream_formula.hasNext() ) {
                            adaptor.addChild(root_1, stream_formula.nextTree());

                        }
                        stream_formula.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 11 :
                    // TcSimple.g:294:3: KEYWORD_LOOKDOWNX PAREN_OPEN tableref COMMA formula ( COMMA formula )* PAREN_CLOSE
                    {
                    KEYWORD_LOOKDOWNX200=(Token)match(input,KEYWORD_LOOKDOWNX,FOLLOW_KEYWORD_LOOKDOWNX_in_expression3678);  
                    stream_KEYWORD_LOOKDOWNX.add(KEYWORD_LOOKDOWNX200);


                    PAREN_OPEN201=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3680);  
                    stream_PAREN_OPEN.add(PAREN_OPEN201);


                    pushFollow(FOLLOW_tableref_in_expression3682);
                    tableref202=tableref();

                    state._fsp--;

                    stream_tableref.add(tableref202.getTree());

                    COMMA203=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3684);  
                    stream_COMMA.add(COMMA203);


                    pushFollow(FOLLOW_formula_in_expression3686);
                    formula204=formula();

                    state._fsp--;

                    stream_formula.add(formula204.getTree());

                    // TcSimple.g:294:55: ( COMMA formula )*
                    loop41:
                    do {
                        int alt41=2;
                        int LA41_0 = input.LA(1);

                        if ( (LA41_0==COMMA) ) {
                            alt41=1;
                        }


                        switch (alt41) {
                    	case 1 :
                    	    // TcSimple.g:294:56: COMMA formula
                    	    {
                    	    COMMA205=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3689);  
                    	    stream_COMMA.add(COMMA205);


                    	    pushFollow(FOLLOW_formula_in_expression3691);
                    	    formula206=formula();

                    	    state._fsp--;

                    	    stream_formula.add(formula206.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop41;
                        }
                    } while (true);


                    PAREN_CLOSE207=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3695);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE207);


                    // AST REWRITE
                    // elements: formula, tableref, KEYWORD_LOOKDOWNX
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 295:11: -> ^( KEYWORD_LOOKDOWNX tableref ( formula )* )
                    {
                        // TcSimple.g:295:14: ^( KEYWORD_LOOKDOWNX tableref ( formula )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_LOOKDOWNX.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_tableref.nextTree());

                        // TcSimple.g:295:43: ( formula )*
                        while ( stream_formula.hasNext() ) {
                            adaptor.addChild(root_1, stream_formula.nextTree());

                        }
                        stream_formula.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 12 :
                    // TcSimple.g:296:3: KEYWORD_EXISTS PAREN_OPEN tableref COMMA formula ( COMMA formula )* PAREN_CLOSE
                    {
                    KEYWORD_EXISTS208=(Token)match(input,KEYWORD_EXISTS,FOLLOW_KEYWORD_EXISTS_in_expression3720);  
                    stream_KEYWORD_EXISTS.add(KEYWORD_EXISTS208);


                    PAREN_OPEN209=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3722);  
                    stream_PAREN_OPEN.add(PAREN_OPEN209);


                    pushFollow(FOLLOW_tableref_in_expression3724);
                    tableref210=tableref();

                    state._fsp--;

                    stream_tableref.add(tableref210.getTree());

                    COMMA211=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3726);  
                    stream_COMMA.add(COMMA211);


                    pushFollow(FOLLOW_formula_in_expression3728);
                    formula212=formula();

                    state._fsp--;

                    stream_formula.add(formula212.getTree());

                    // TcSimple.g:296:53: ( COMMA formula )*
                    loop42:
                    do {
                        int alt42=2;
                        int LA42_0 = input.LA(1);

                        if ( (LA42_0==COMMA) ) {
                            alt42=1;
                        }


                        switch (alt42) {
                    	case 1 :
                    	    // TcSimple.g:296:54: COMMA formula
                    	    {
                    	    COMMA213=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3732);  
                    	    stream_COMMA.add(COMMA213);


                    	    pushFollow(FOLLOW_formula_in_expression3734);
                    	    formula214=formula();

                    	    state._fsp--;

                    	    stream_formula.add(formula214.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop42;
                        }
                    } while (true);


                    PAREN_CLOSE215=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3738);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE215);


                    // AST REWRITE
                    // elements: KEYWORD_EXISTS, tableref, formula
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 297:11: -> ^( KEYWORD_EXISTS tableref ( formula )* )
                    {
                        // TcSimple.g:297:14: ^( KEYWORD_EXISTS tableref ( formula )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_EXISTS.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_tableref.nextTree());

                        // TcSimple.g:297:40: ( formula )*
                        while ( stream_formula.hasNext() ) {
                            adaptor.addChild(root_1, stream_formula.nextTree());

                        }
                        stream_formula.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 13 :
                    // TcSimple.g:298:3: KEYWORD_INTERPOL PAREN_OPEN tableref COMMA formula PAREN_CLOSE
                    {
                    KEYWORD_INTERPOL216=(Token)match(input,KEYWORD_INTERPOL,FOLLOW_KEYWORD_INTERPOL_in_expression3763);  
                    stream_KEYWORD_INTERPOL.add(KEYWORD_INTERPOL216);


                    PAREN_OPEN217=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3765);  
                    stream_PAREN_OPEN.add(PAREN_OPEN217);


                    pushFollow(FOLLOW_tableref_in_expression3767);
                    tableref218=tableref();

                    state._fsp--;

                    stream_tableref.add(tableref218.getTree());

                    COMMA219=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3769);  
                    stream_COMMA.add(COMMA219);


                    pushFollow(FOLLOW_formula_in_expression3771);
                    formula220=formula();

                    state._fsp--;

                    stream_formula.add(formula220.getTree());

                    PAREN_CLOSE221=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3773);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE221);


                    // AST REWRITE
                    // elements: formula, tableref, KEYWORD_INTERPOL
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 299:11: -> ^( KEYWORD_INTERPOL tableref formula )
                    {
                        // TcSimple.g:299:14: ^( KEYWORD_INTERPOL tableref formula )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_INTERPOL.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_tableref.nextTree());

                        adaptor.addChild(root_1, stream_formula.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 14 :
                    // TcSimple.g:300:3: KEYWORD_TABCOLS PAREN_OPEN tableref PAREN_CLOSE
                    {
                    KEYWORD_TABCOLS222=(Token)match(input,KEYWORD_TABCOLS,FOLLOW_KEYWORD_TABCOLS_in_expression3797);  
                    stream_KEYWORD_TABCOLS.add(KEYWORD_TABCOLS222);


                    PAREN_OPEN223=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3799);  
                    stream_PAREN_OPEN.add(PAREN_OPEN223);


                    pushFollow(FOLLOW_tableref_in_expression3801);
                    tableref224=tableref();

                    state._fsp--;

                    stream_tableref.add(tableref224.getTree());

                    PAREN_CLOSE225=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3803);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE225);


                    // AST REWRITE
                    // elements: tableref, KEYWORD_TABCOLS
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 301:11: -> ^( KEYWORD_TABCOLS tableref )
                    {
                        // TcSimple.g:301:14: ^( KEYWORD_TABCOLS tableref )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_TABCOLS.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_tableref.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 15 :
                    // TcSimple.g:302:3: KEYWORD_TABROWS PAREN_OPEN tableref PAREN_CLOSE
                    {
                    KEYWORD_TABROWS226=(Token)match(input,KEYWORD_TABROWS,FOLLOW_KEYWORD_TABROWS_in_expression3825);  
                    stream_KEYWORD_TABROWS.add(KEYWORD_TABROWS226);


                    PAREN_OPEN227=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3827);  
                    stream_PAREN_OPEN.add(PAREN_OPEN227);


                    pushFollow(FOLLOW_tableref_in_expression3829);
                    tableref228=tableref();

                    state._fsp--;

                    stream_tableref.add(tableref228.getTree());

                    PAREN_CLOSE229=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3831);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE229);


                    // AST REWRITE
                    // elements: KEYWORD_TABROWS, tableref
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 303:11: -> ^( KEYWORD_TABROWS tableref )
                    {
                        // TcSimple.g:303:14: ^( KEYWORD_TABROWS tableref )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_TABROWS.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_tableref.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 16 :
                    // TcSimple.g:304:3: KEYWORD_FUNCREF PAREN_OPEN formula PAREN_CLOSE
                    {
                    KEYWORD_FUNCREF230=(Token)match(input,KEYWORD_FUNCREF,FOLLOW_KEYWORD_FUNCREF_in_expression3853);  
                    stream_KEYWORD_FUNCREF.add(KEYWORD_FUNCREF230);


                    PAREN_OPEN231=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3855);  
                    stream_PAREN_OPEN.add(PAREN_OPEN231);


                    pushFollow(FOLLOW_formula_in_expression3857);
                    formula232=formula();

                    state._fsp--;

                    stream_formula.add(formula232.getTree());

                    PAREN_CLOSE233=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3859);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE233);


                    // AST REWRITE
                    // elements: formula, KEYWORD_FUNCREF
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 305:11: -> ^( KEYWORD_FUNCREF formula )
                    {
                        // TcSimple.g:305:14: ^( KEYWORD_FUNCREF formula )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_FUNCREF.nextNode()
                        , root_1);

                        adaptor.addChild(root_1, stream_formula.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 17 :
                    // TcSimple.g:306:3: KEYWORD_DOCALL PAREN_OPEN formula ( COMMA formula )* PAREN_CLOSE
                    {
                    KEYWORD_DOCALL234=(Token)match(input,KEYWORD_DOCALL,FOLLOW_KEYWORD_DOCALL_in_expression3881);  
                    stream_KEYWORD_DOCALL.add(KEYWORD_DOCALL234);


                    PAREN_OPEN235=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3883);  
                    stream_PAREN_OPEN.add(PAREN_OPEN235);


                    pushFollow(FOLLOW_formula_in_expression3885);
                    formula236=formula();

                    state._fsp--;

                    stream_formula.add(formula236.getTree());

                    // TcSimple.g:306:37: ( COMMA formula )*
                    loop43:
                    do {
                        int alt43=2;
                        int LA43_0 = input.LA(1);

                        if ( (LA43_0==COMMA) ) {
                            alt43=1;
                        }


                        switch (alt43) {
                    	case 1 :
                    	    // TcSimple.g:306:38: COMMA formula
                    	    {
                    	    COMMA237=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3888);  
                    	    stream_COMMA.add(COMMA237);


                    	    pushFollow(FOLLOW_formula_in_expression3890);
                    	    formula238=formula();

                    	    state._fsp--;

                    	    stream_formula.add(formula238.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop43;
                        }
                    } while (true);


                    PAREN_CLOSE239=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3894);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE239);


                    // AST REWRITE
                    // elements: KEYWORD_DOCALL, formula
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 307:11: -> ^( KEYWORD_DOCALL ( formula )* )
                    {
                        // TcSimple.g:307:14: ^( KEYWORD_DOCALL ( formula )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_DOCALL.nextNode()
                        , root_1);

                        // TcSimple.g:307:31: ( formula )*
                        while ( stream_formula.hasNext() ) {
                            adaptor.addChild(root_1, stream_formula.nextTree());

                        }
                        stream_formula.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 18 :
                    // TcSimple.g:308:3: KEYWORD_COUNTERLIST PAREN_OPEN id ( COMMA id )* PAREN_CLOSE
                    {
                    KEYWORD_COUNTERLIST240=(Token)match(input,KEYWORD_COUNTERLIST,FOLLOW_KEYWORD_COUNTERLIST_in_expression3917);  
                    stream_KEYWORD_COUNTERLIST.add(KEYWORD_COUNTERLIST240);


                    PAREN_OPEN241=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_expression3919);  
                    stream_PAREN_OPEN.add(PAREN_OPEN241);


                    pushFollow(FOLLOW_id_in_expression3921);
                    id242=id();

                    state._fsp--;

                    stream_id.add(id242.getTree());

                    // TcSimple.g:308:37: ( COMMA id )*
                    loop44:
                    do {
                        int alt44=2;
                        int LA44_0 = input.LA(1);

                        if ( (LA44_0==COMMA) ) {
                            alt44=1;
                        }


                        switch (alt44) {
                    	case 1 :
                    	    // TcSimple.g:308:38: COMMA id
                    	    {
                    	    COMMA243=(Token)match(input,COMMA,FOLLOW_COMMA_in_expression3924);  
                    	    stream_COMMA.add(COMMA243);


                    	    pushFollow(FOLLOW_id_in_expression3926);
                    	    id244=id();

                    	    state._fsp--;

                    	    stream_id.add(id244.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop44;
                        }
                    } while (true);


                    PAREN_CLOSE245=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_expression3930);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE245);


                    // AST REWRITE
                    // elements: id, KEYWORD_COUNTERLIST
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 309:11: -> ^( KEYWORD_COUNTERLIST ( id )* )
                    {
                        // TcSimple.g:309:14: ^( KEYWORD_COUNTERLIST ( id )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        stream_KEYWORD_COUNTERLIST.nextNode()
                        , root_1);

                        // TcSimple.g:309:36: ( id )*
                        while ( stream_id.hasNext() ) {
                            adaptor.addChild(root_1, stream_id.nextTree());

                        }
                        stream_id.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 19 :
                    // TcSimple.g:310:3: ID
                    {
                    ID246=(Token)match(input,ID,FOLLOW_ID_in_expression3953);  
                    stream_ID.add(ID246);


                    // AST REWRITE
                    // elements: ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 310:19: -> ^( TT_USEID ID )
                    {
                        // TcSimple.g:310:22: ^( TT_USEID ID )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        (TcAst)adaptor.create(TT_USEID, "TT_USEID")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 20 :
                    // TcSimple.g:311:3: ID DOT id
                    {
                    ID247=(Token)match(input,ID,FOLLOW_ID_in_expression3996);  
                    stream_ID.add(ID247);


                    DOT248=(Token)match(input,DOT,FOLLOW_DOT_in_expression3999);  
                    stream_DOT.add(DOT248);


                    pushFollow(FOLLOW_id_in_expression4002);
                    id249=id();

                    state._fsp--;

                    stream_id.add(id249.getTree());

                    // AST REWRITE
                    // elements: ID, id
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 311:19: -> ^( TT_INPUTCALCCALLSIMPLE ID id )
                    {
                        // TcSimple.g:311:22: ^( TT_INPUTCALCCALLSIMPLE ID id )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        (TcAst)adaptor.create(TT_INPUTCALCCALLSIMPLE, "TT_INPUTCALCCALLSIMPLE")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        adaptor.addChild(root_1, stream_id.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 21 :
                    // TcSimple.g:312:3: ID index ( columnaccess )?
                    {
                    ID250=(Token)match(input,ID,FOLLOW_ID_in_expression4027);  
                    stream_ID.add(ID250);


                    pushFollow(FOLLOW_index_in_expression4030);
                    index251=index();

                    state._fsp--;

                    stream_index.add(index251.getTree());

                    // TcSimple.g:312:13: ( columnaccess )?
                    int alt45=2;
                    int LA45_0 = input.LA(1);

                    if ( (LA45_0==DOT||LA45_0==PAREN_OPEN) ) {
                        alt45=1;
                    }
                    switch (alt45) {
                        case 1 :
                            // TcSimple.g:312:13: columnaccess
                            {
                            pushFollow(FOLLOW_columnaccess_in_expression4032);
                            columnaccess252=columnaccess();

                            state._fsp--;

                            stream_columnaccess.add(columnaccess252.getTree());

                            }
                            break;

                    }


                    // AST REWRITE
                    // elements: ID, columnaccess, index
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 312:27: -> ^( TT_INPUTORTABACCESSWITHINDEX ID index ( columnaccess )? )
                    {
                        // TcSimple.g:312:30: ^( TT_INPUTORTABACCESSWITHINDEX ID index ( columnaccess )? )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        (TcAst)adaptor.create(TT_INPUTORTABACCESSWITHINDEX, "TT_INPUTORTABACCESSWITHINDEX")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        adaptor.addChild(root_1, stream_index.nextTree());

                        // TcSimple.g:312:70: ( columnaccess )?
                        if ( stream_columnaccess.hasNext() ) {
                            adaptor.addChild(root_1, stream_columnaccess.nextTree());

                        }
                        stream_columnaccess.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 22 :
                    // TcSimple.g:316:3: dyntable ( index ( columnaccess )? )?
                    {
                    pushFollow(FOLLOW_dyntable_in_expression4209);
                    dyntable253=dyntable();

                    state._fsp--;

                    stream_dyntable.add(dyntable253.getTree());

                    // TcSimple.g:316:12: ( index ( columnaccess )? )?
                    int alt47=2;
                    int LA47_0 = input.LA(1);

                    if ( (LA47_0==BRACKET_OPEN) ) {
                        alt47=1;
                    }
                    switch (alt47) {
                        case 1 :
                            // TcSimple.g:316:13: index ( columnaccess )?
                            {
                            pushFollow(FOLLOW_index_in_expression4212);
                            index254=index();

                            state._fsp--;

                            stream_index.add(index254.getTree());

                            // TcSimple.g:316:19: ( columnaccess )?
                            int alt46=2;
                            int LA46_0 = input.LA(1);

                            if ( (LA46_0==DOT||LA46_0==PAREN_OPEN) ) {
                                alt46=1;
                            }
                            switch (alt46) {
                                case 1 :
                                    // TcSimple.g:316:19: columnaccess
                                    {
                                    pushFollow(FOLLOW_columnaccess_in_expression4214);
                                    columnaccess255=columnaccess();

                                    state._fsp--;

                                    stream_columnaccess.add(columnaccess255.getTree());

                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    // AST REWRITE
                    // elements: dyntable, columnaccess, index
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 316:35: -> ^( TT_DYNTABLE dyntable ( index )? ( columnaccess )? )
                    {
                        // TcSimple.g:316:38: ^( TT_DYNTABLE dyntable ( index )? ( columnaccess )? )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        (TcAst)adaptor.create(TT_DYNTABLE, "TT_DYNTABLE")
                        , root_1);

                        adaptor.addChild(root_1, stream_dyntable.nextTree());

                        // TcSimple.g:316:61: ( index )?
                        if ( stream_index.hasNext() ) {
                            adaptor.addChild(root_1, stream_index.nextTree());

                        }
                        stream_index.reset();

                        // TcSimple.g:316:68: ( columnaccess )?
                        if ( stream_columnaccess.hasNext() ) {
                            adaptor.addChild(root_1, stream_columnaccess.nextTree());

                        }
                        stream_columnaccess.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 23 :
                    // TcSimple.g:318:3: ID parameterListe
                    {
                    ID256=(Token)match(input,ID,FOLLOW_ID_in_expression4288);  
                    stream_ID.add(ID256);


                    pushFollow(FOLLOW_parameterListe_in_expression4291);
                    parameterListe257=parameterListe();

                    state._fsp--;

                    stream_parameterListe.add(parameterListe257.getTree());

                    // AST REWRITE
                    // elements: parameterListe, ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 318:35: -> ^( TT_FUNORCALCCALL ID parameterListe )
                    {
                        // TcSimple.g:318:38: ^( TT_FUNORCALCCALL ID parameterListe )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        (TcAst)adaptor.create(TT_FUNORCALCCALL, "TT_FUNORCALCCALL")
                        , root_1);

                        adaptor.addChild(root_1, 
                        stream_ID.nextNode()
                        );

                        adaptor.addChild(root_1, stream_parameterListe.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 24 :
                    // TcSimple.g:319:3: ifstmt
                    {
                    root_0 = (TcAst)adaptor.nil();


                    pushFollow(FOLLOW_ifstmt_in_expression4324);
                    ifstmt258=ifstmt();

                    state._fsp--;

                    adaptor.addChild(root_0, ifstmt258.getTree());

                    }
                    break;
                case 25 :
                    // TcSimple.g:320:3: casestmt
                    {
                    root_0 = (TcAst)adaptor.nil();


                    pushFollow(FOLLOW_casestmt_in_expression4328);
                    casestmt259=casestmt();

                    state._fsp--;

                    adaptor.addChild(root_0, casestmt259.getTree());

                    }
                    break;
                case 26 :
                    // TcSimple.g:321:3: constant
                    {
                    root_0 = (TcAst)adaptor.nil();


                    pushFollow(FOLLOW_constant_in_expression4332);
                    constant260=constant();

                    state._fsp--;

                    adaptor.addChild(root_0, constant260.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "expression"


    public static class tableref_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "tableref"
    // TcSimple.g:324:1: tableref : ( id | dyntable );
    public final TcSimpleParser.tableref_return tableref() throws RecognitionException {
        TcSimpleParser.tableref_return retval = new TcSimpleParser.tableref_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        TcSimpleParser.id_return id261 =null;

        TcSimpleParser.dyntable_return dyntable262 =null;



        try {
            // TcSimple.g:324:9: ( id | dyntable )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( ((LA49_0 >= ID && LA49_0 <= KEYWORD_INTERPOL)||(LA49_0 >= KEYWORD_LOOKDOWNX && LA49_0 <= KEYWORD_TABLE)||(LA49_0 >= KEYWORD_TABROWS && LA49_0 <= KEYWORD_THEN)||(LA49_0 >= KEYWORD_TREE && LA49_0 <= KEYWORD_WHEN)) ) {
                alt49=1;
            }
            else if ( (LA49_0==KEYWORD_TABREF) ) {
                alt49=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;

            }
            switch (alt49) {
                case 1 :
                    // TcSimple.g:325:3: id
                    {
                    root_0 = (TcAst)adaptor.nil();


                    pushFollow(FOLLOW_id_in_tableref4413);
                    id261=id();

                    state._fsp--;

                    adaptor.addChild(root_0, id261.getTree());

                    }
                    break;
                case 2 :
                    // TcSimple.g:326:3: dyntable
                    {
                    root_0 = (TcAst)adaptor.nil();


                    pushFollow(FOLLOW_dyntable_in_tableref4417);
                    dyntable262=dyntable();

                    state._fsp--;

                    adaptor.addChild(root_0, dyntable262.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "tableref"


    public static class range_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "range"
    // TcSimple.g:329:1: range : formula ( DOTS formula )? -> ^( TT_RANGE ( formula )* ) ;
    public final TcSimpleParser.range_return range() throws RecognitionException {
        TcSimpleParser.range_return retval = new TcSimpleParser.range_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token DOTS264=null;
        TcSimpleParser.formula_return formula263 =null;

        TcSimpleParser.formula_return formula265 =null;


        TcAst DOTS264_tree=null;
        RewriteRuleTokenStream stream_DOTS=new RewriteRuleTokenStream(adaptor,"token DOTS");
        RewriteRuleSubtreeStream stream_formula=new RewriteRuleSubtreeStream(adaptor,"rule formula");
        try {
            // TcSimple.g:329:6: ( formula ( DOTS formula )? -> ^( TT_RANGE ( formula )* ) )
            // TcSimple.g:330:3: formula ( DOTS formula )?
            {
            pushFollow(FOLLOW_formula_in_range4492);
            formula263=formula();

            state._fsp--;

            stream_formula.add(formula263.getTree());

            // TcSimple.g:330:11: ( DOTS formula )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==DOTS) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // TcSimple.g:330:12: DOTS formula
                    {
                    DOTS264=(Token)match(input,DOTS,FOLLOW_DOTS_in_range4495);  
                    stream_DOTS.add(DOTS264);


                    pushFollow(FOLLOW_formula_in_range4497);
                    formula265=formula();

                    state._fsp--;

                    stream_formula.add(formula265.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: formula
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 330:35: -> ^( TT_RANGE ( formula )* )
            {
                // TcSimple.g:330:38: ^( TT_RANGE ( formula )* )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                (TcAst)adaptor.create(TT_RANGE, "TT_RANGE")
                , root_1);

                // TcSimple.g:330:49: ( formula )*
                while ( stream_formula.hasNext() ) {
                    adaptor.addChild(root_1, stream_formula.nextTree());

                }
                stream_formula.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "range"


    public static class ifstmt_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "ifstmt"
    // TcSimple.g:333:1: ifstmt : KEYWORD_IF formula KEYWORD_THEN formula KEYWORD_ELSE formula KEYWORD_ENDIF -> ^( KEYWORD_IF ( formula )* ) ;
    public final TcSimpleParser.ifstmt_return ifstmt() throws RecognitionException {
        TcSimpleParser.ifstmt_return retval = new TcSimpleParser.ifstmt_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token KEYWORD_IF266=null;
        Token KEYWORD_THEN268=null;
        Token KEYWORD_ELSE270=null;
        Token KEYWORD_ENDIF272=null;
        TcSimpleParser.formula_return formula267 =null;

        TcSimpleParser.formula_return formula269 =null;

        TcSimpleParser.formula_return formula271 =null;


        TcAst KEYWORD_IF266_tree=null;
        TcAst KEYWORD_THEN268_tree=null;
        TcAst KEYWORD_ELSE270_tree=null;
        TcAst KEYWORD_ENDIF272_tree=null;
        RewriteRuleTokenStream stream_KEYWORD_ENDIF=new RewriteRuleTokenStream(adaptor,"token KEYWORD_ENDIF");
        RewriteRuleTokenStream stream_KEYWORD_THEN=new RewriteRuleTokenStream(adaptor,"token KEYWORD_THEN");
        RewriteRuleTokenStream stream_KEYWORD_ELSE=new RewriteRuleTokenStream(adaptor,"token KEYWORD_ELSE");
        RewriteRuleTokenStream stream_KEYWORD_IF=new RewriteRuleTokenStream(adaptor,"token KEYWORD_IF");
        RewriteRuleSubtreeStream stream_formula=new RewriteRuleSubtreeStream(adaptor,"rule formula");
        try {
            // TcSimple.g:333:7: ( KEYWORD_IF formula KEYWORD_THEN formula KEYWORD_ELSE formula KEYWORD_ENDIF -> ^( KEYWORD_IF ( formula )* ) )
            // TcSimple.g:334:3: KEYWORD_IF formula KEYWORD_THEN formula KEYWORD_ELSE formula KEYWORD_ENDIF
            {
            KEYWORD_IF266=(Token)match(input,KEYWORD_IF,FOLLOW_KEYWORD_IF_in_ifstmt4526);  
            stream_KEYWORD_IF.add(KEYWORD_IF266);


            pushFollow(FOLLOW_formula_in_ifstmt4528);
            formula267=formula();

            state._fsp--;

            stream_formula.add(formula267.getTree());

            KEYWORD_THEN268=(Token)match(input,KEYWORD_THEN,FOLLOW_KEYWORD_THEN_in_ifstmt4530);  
            stream_KEYWORD_THEN.add(KEYWORD_THEN268);


            pushFollow(FOLLOW_formula_in_ifstmt4532);
            formula269=formula();

            state._fsp--;

            stream_formula.add(formula269.getTree());

            KEYWORD_ELSE270=(Token)match(input,KEYWORD_ELSE,FOLLOW_KEYWORD_ELSE_in_ifstmt4534);  
            stream_KEYWORD_ELSE.add(KEYWORD_ELSE270);


            pushFollow(FOLLOW_formula_in_ifstmt4536);
            formula271=formula();

            state._fsp--;

            stream_formula.add(formula271.getTree());

            KEYWORD_ENDIF272=(Token)match(input,KEYWORD_ENDIF,FOLLOW_KEYWORD_ENDIF_in_ifstmt4538);  
            stream_KEYWORD_ENDIF.add(KEYWORD_ENDIF272);


            // AST REWRITE
            // elements: formula, KEYWORD_IF
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 335:35: -> ^( KEYWORD_IF ( formula )* )
            {
                // TcSimple.g:335:38: ^( KEYWORD_IF ( formula )* )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                stream_KEYWORD_IF.nextNode()
                , root_1);

                // TcSimple.g:335:51: ( formula )*
                while ( stream_formula.hasNext() ) {
                    adaptor.addChild(root_1, stream_formula.nextTree());

                }
                stream_formula.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "ifstmt"


    public static class casestmt_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "casestmt"
    // TcSimple.g:338:1: casestmt : KEYWORD_CASE formula ( casewhen )* ( casedefault )? KEYWORD_ENDCASE -> ^( KEYWORD_CASE formula ( casewhen )* ( casedefault )? ) ;
    public final TcSimpleParser.casestmt_return casestmt() throws RecognitionException {
        TcSimpleParser.casestmt_return retval = new TcSimpleParser.casestmt_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token KEYWORD_CASE273=null;
        Token KEYWORD_ENDCASE277=null;
        TcSimpleParser.formula_return formula274 =null;

        TcSimpleParser.casewhen_return casewhen275 =null;

        TcSimpleParser.casedefault_return casedefault276 =null;


        TcAst KEYWORD_CASE273_tree=null;
        TcAst KEYWORD_ENDCASE277_tree=null;
        RewriteRuleTokenStream stream_KEYWORD_CASE=new RewriteRuleTokenStream(adaptor,"token KEYWORD_CASE");
        RewriteRuleTokenStream stream_KEYWORD_ENDCASE=new RewriteRuleTokenStream(adaptor,"token KEYWORD_ENDCASE");
        RewriteRuleSubtreeStream stream_casedefault=new RewriteRuleSubtreeStream(adaptor,"rule casedefault");
        RewriteRuleSubtreeStream stream_casewhen=new RewriteRuleSubtreeStream(adaptor,"rule casewhen");
        RewriteRuleSubtreeStream stream_formula=new RewriteRuleSubtreeStream(adaptor,"rule formula");
        try {
            // TcSimple.g:338:9: ( KEYWORD_CASE formula ( casewhen )* ( casedefault )? KEYWORD_ENDCASE -> ^( KEYWORD_CASE formula ( casewhen )* ( casedefault )? ) )
            // TcSimple.g:339:3: KEYWORD_CASE formula ( casewhen )* ( casedefault )? KEYWORD_ENDCASE
            {
            KEYWORD_CASE273=(Token)match(input,KEYWORD_CASE,FOLLOW_KEYWORD_CASE_in_casestmt4616);  
            stream_KEYWORD_CASE.add(KEYWORD_CASE273);


            pushFollow(FOLLOW_formula_in_casestmt4618);
            formula274=formula();

            state._fsp--;

            stream_formula.add(formula274.getTree());

            // TcSimple.g:339:24: ( casewhen )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==KEYWORD_WHEN) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // TcSimple.g:339:24: casewhen
            	    {
            	    pushFollow(FOLLOW_casewhen_in_casestmt4620);
            	    casewhen275=casewhen();

            	    state._fsp--;

            	    stream_casewhen.add(casewhen275.getTree());

            	    }
            	    break;

            	default :
            	    break loop51;
                }
            } while (true);


            // TcSimple.g:339:34: ( casedefault )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==KEYWORD_DEFAULT) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // TcSimple.g:339:34: casedefault
                    {
                    pushFollow(FOLLOW_casedefault_in_casestmt4623);
                    casedefault276=casedefault();

                    state._fsp--;

                    stream_casedefault.add(casedefault276.getTree());

                    }
                    break;

            }


            KEYWORD_ENDCASE277=(Token)match(input,KEYWORD_ENDCASE,FOLLOW_KEYWORD_ENDCASE_in_casestmt4626);  
            stream_KEYWORD_ENDCASE.add(KEYWORD_ENDCASE277);


            // AST REWRITE
            // elements: casewhen, KEYWORD_CASE, casedefault, formula
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 340:35: -> ^( KEYWORD_CASE formula ( casewhen )* ( casedefault )? )
            {
                // TcSimple.g:340:38: ^( KEYWORD_CASE formula ( casewhen )* ( casedefault )? )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                stream_KEYWORD_CASE.nextNode()
                , root_1);

                adaptor.addChild(root_1, stream_formula.nextTree());

                // TcSimple.g:340:61: ( casewhen )*
                while ( stream_casewhen.hasNext() ) {
                    adaptor.addChild(root_1, stream_casewhen.nextTree());

                }
                stream_casewhen.reset();

                // TcSimple.g:340:71: ( casedefault )?
                if ( stream_casedefault.hasNext() ) {
                    adaptor.addChild(root_1, stream_casedefault.nextTree());

                }
                stream_casedefault.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "casestmt"


    public static class casewhen_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "casewhen"
    // TcSimple.g:343:1: casewhen : KEYWORD_WHEN casecompares COLON formula -> ^( KEYWORD_WHEN casecompares formula ) ;
    public final TcSimpleParser.casewhen_return casewhen() throws RecognitionException {
        TcSimpleParser.casewhen_return retval = new TcSimpleParser.casewhen_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token KEYWORD_WHEN278=null;
        Token COLON280=null;
        TcSimpleParser.casecompares_return casecompares279 =null;

        TcSimpleParser.formula_return formula281 =null;


        TcAst KEYWORD_WHEN278_tree=null;
        TcAst COLON280_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleTokenStream stream_KEYWORD_WHEN=new RewriteRuleTokenStream(adaptor,"token KEYWORD_WHEN");
        RewriteRuleSubtreeStream stream_casecompares=new RewriteRuleSubtreeStream(adaptor,"rule casecompares");
        RewriteRuleSubtreeStream stream_formula=new RewriteRuleSubtreeStream(adaptor,"rule formula");
        try {
            // TcSimple.g:343:9: ( KEYWORD_WHEN casecompares COLON formula -> ^( KEYWORD_WHEN casecompares formula ) )
            // TcSimple.g:344:3: KEYWORD_WHEN casecompares COLON formula
            {
            KEYWORD_WHEN278=(Token)match(input,KEYWORD_WHEN,FOLLOW_KEYWORD_WHEN_in_casewhen4767);  
            stream_KEYWORD_WHEN.add(KEYWORD_WHEN278);


            pushFollow(FOLLOW_casecompares_in_casewhen4769);
            casecompares279=casecompares();

            state._fsp--;

            stream_casecompares.add(casecompares279.getTree());

            COLON280=(Token)match(input,COLON,FOLLOW_COLON_in_casewhen4771);  
            stream_COLON.add(COLON280);


            pushFollow(FOLLOW_formula_in_casewhen4773);
            formula281=formula();

            state._fsp--;

            stream_formula.add(formula281.getTree());

            // AST REWRITE
            // elements: formula, casecompares, KEYWORD_WHEN
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 344:45: -> ^( KEYWORD_WHEN casecompares formula )
            {
                // TcSimple.g:344:48: ^( KEYWORD_WHEN casecompares formula )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                stream_KEYWORD_WHEN.nextNode()
                , root_1);

                adaptor.addChild(root_1, stream_casecompares.nextTree());

                adaptor.addChild(root_1, stream_formula.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "casewhen"


    public static class casecompares_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "casecompares"
    // TcSimple.g:346:1: casecompares : casecompare ( COMMA casecompare )* -> ^( TT_CASECONDITION ( casecompare )* ) ;
    public final TcSimpleParser.casecompares_return casecompares() throws RecognitionException {
        TcSimpleParser.casecompares_return retval = new TcSimpleParser.casecompares_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token COMMA283=null;
        TcSimpleParser.casecompare_return casecompare282 =null;

        TcSimpleParser.casecompare_return casecompare284 =null;


        TcAst COMMA283_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleSubtreeStream stream_casecompare=new RewriteRuleSubtreeStream(adaptor,"rule casecompare");
        try {
            // TcSimple.g:346:13: ( casecompare ( COMMA casecompare )* -> ^( TT_CASECONDITION ( casecompare )* ) )
            // TcSimple.g:347:3: casecompare ( COMMA casecompare )*
            {
            pushFollow(FOLLOW_casecompare_in_casecompares4800);
            casecompare282=casecompare();

            state._fsp--;

            stream_casecompare.add(casecompare282.getTree());

            // TcSimple.g:347:15: ( COMMA casecompare )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==COMMA) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // TcSimple.g:347:16: COMMA casecompare
            	    {
            	    COMMA283=(Token)match(input,COMMA,FOLLOW_COMMA_in_casecompares4803);  
            	    stream_COMMA.add(COMMA283);


            	    pushFollow(FOLLOW_casecompare_in_casecompares4805);
            	    casecompare284=casecompare();

            	    state._fsp--;

            	    stream_casecompare.add(casecompare284.getTree());

            	    }
            	    break;

            	default :
            	    break loop53;
                }
            } while (true);


            // AST REWRITE
            // elements: casecompare
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 347:38: -> ^( TT_CASECONDITION ( casecompare )* )
            {
                // TcSimple.g:347:41: ^( TT_CASECONDITION ( casecompare )* )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                (TcAst)adaptor.create(TT_CASECONDITION, "TT_CASECONDITION")
                , root_1);

                // TcSimple.g:347:60: ( casecompare )*
                while ( stream_casecompare.hasNext() ) {
                    adaptor.addChild(root_1, stream_casecompare.nextTree());

                }
                stream_casecompare.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "casecompares"


    public static class casecompare_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "casecompare"
    // TcSimple.g:350:1: casecompare : ( caseconstant -> ^( TT_CASECOMPARISON caseconstant ) | caseconstantnumber DOTS caseconstantnumber -> ^( TT_CASERANGE ( caseconstantnumber )* ) | STRING DOTS STRING -> ^( TT_CASERANGE ( STRING )* ) );
    public final TcSimpleParser.casecompare_return casecompare() throws RecognitionException {
        TcSimpleParser.casecompare_return retval = new TcSimpleParser.casecompare_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token DOTS287=null;
        Token STRING289=null;
        Token DOTS290=null;
        Token STRING291=null;
        TcSimpleParser.caseconstant_return caseconstant285 =null;

        TcSimpleParser.caseconstantnumber_return caseconstantnumber286 =null;

        TcSimpleParser.caseconstantnumber_return caseconstantnumber288 =null;


        TcAst DOTS287_tree=null;
        TcAst STRING289_tree=null;
        TcAst DOTS290_tree=null;
        TcAst STRING291_tree=null;
        RewriteRuleTokenStream stream_DOTS=new RewriteRuleTokenStream(adaptor,"token DOTS");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_caseconstantnumber=new RewriteRuleSubtreeStream(adaptor,"rule caseconstantnumber");
        RewriteRuleSubtreeStream stream_caseconstant=new RewriteRuleSubtreeStream(adaptor,"rule caseconstant");
        try {
            // TcSimple.g:350:12: ( caseconstant -> ^( TT_CASECOMPARISON caseconstant ) | caseconstantnumber DOTS caseconstantnumber -> ^( TT_CASERANGE ( caseconstantnumber )* ) | STRING DOTS STRING -> ^( TT_CASERANGE ( STRING )* ) )
            int alt54=3;
            switch ( input.LA(1) ) {
            case STRING:
                {
                int LA54_1 = input.LA(2);

                if ( (LA54_1==DOTS) ) {
                    alt54=3;
                }
                else if ( ((LA54_1 >= COLON && LA54_1 <= COMMA)) ) {
                    alt54=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 54, 1, input);

                    throw nvae;

                }
                }
                break;
            case NUMBER:
                {
                int LA54_2 = input.LA(2);

                if ( ((LA54_2 >= COLON && LA54_2 <= COMMA)) ) {
                    alt54=1;
                }
                else if ( (LA54_2==DOTS) ) {
                    alt54=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 54, 2, input);

                    throw nvae;

                }
                }
                break;
            case MINUS:
                {
                int LA54_3 = input.LA(2);

                if ( (LA54_3==NUMBER) ) {
                    int LA54_7 = input.LA(3);

                    if ( ((LA54_7 >= COLON && LA54_7 <= COMMA)) ) {
                        alt54=1;
                    }
                    else if ( (LA54_7==DOTS) ) {
                        alt54=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 54, 7, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 54, 3, input);

                    throw nvae;

                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 54, 0, input);

                throw nvae;

            }

            switch (alt54) {
                case 1 :
                    // TcSimple.g:351:3: caseconstant
                    {
                    pushFollow(FOLLOW_caseconstant_in_casecompare4830);
                    caseconstant285=caseconstant();

                    state._fsp--;

                    stream_caseconstant.add(caseconstant285.getTree());

                    // AST REWRITE
                    // elements: caseconstant
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 351:46: -> ^( TT_CASECOMPARISON caseconstant )
                    {
                        // TcSimple.g:351:49: ^( TT_CASECOMPARISON caseconstant )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        (TcAst)adaptor.create(TT_CASECOMPARISON, "TT_CASECOMPARISON")
                        , root_1);

                        adaptor.addChild(root_1, stream_caseconstant.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // TcSimple.g:352:3: caseconstantnumber DOTS caseconstantnumber
                    {
                    pushFollow(FOLLOW_caseconstantnumber_in_casecompare4872);
                    caseconstantnumber286=caseconstantnumber();

                    state._fsp--;

                    stream_caseconstantnumber.add(caseconstantnumber286.getTree());

                    DOTS287=(Token)match(input,DOTS,FOLLOW_DOTS_in_casecompare4874);  
                    stream_DOTS.add(DOTS287);


                    pushFollow(FOLLOW_caseconstantnumber_in_casecompare4876);
                    caseconstantnumber288=caseconstantnumber();

                    state._fsp--;

                    stream_caseconstantnumber.add(caseconstantnumber288.getTree());

                    // AST REWRITE
                    // elements: caseconstantnumber
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 352:46: -> ^( TT_CASERANGE ( caseconstantnumber )* )
                    {
                        // TcSimple.g:352:49: ^( TT_CASERANGE ( caseconstantnumber )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        (TcAst)adaptor.create(TT_CASERANGE, "TT_CASERANGE")
                        , root_1);

                        // TcSimple.g:352:69: ( caseconstantnumber )*
                        while ( stream_caseconstantnumber.hasNext() ) {
                            adaptor.addChild(root_1, stream_caseconstantnumber.nextTree());

                        }
                        stream_caseconstantnumber.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 3 :
                    // TcSimple.g:353:3: STRING DOTS STRING
                    {
                    STRING289=(Token)match(input,STRING,FOLLOW_STRING_in_casecompare4894);  
                    stream_STRING.add(STRING289);


                    DOTS290=(Token)match(input,DOTS,FOLLOW_DOTS_in_casecompare4896);  
                    stream_DOTS.add(DOTS290);


                    STRING291=(Token)match(input,STRING,FOLLOW_STRING_in_casecompare4898);  
                    stream_STRING.add(STRING291);


                    // AST REWRITE
                    // elements: STRING
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 353:46: -> ^( TT_CASERANGE ( STRING )* )
                    {
                        // TcSimple.g:353:49: ^( TT_CASERANGE ( STRING )* )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        (TcAst)adaptor.create(TT_CASERANGE, "TT_CASERANGE")
                        , root_1);

                        // TcSimple.g:353:69: ( STRING )*
                        while ( stream_STRING.hasNext() ) {
                            adaptor.addChild(root_1, 
                            stream_STRING.nextNode()
                            );

                        }
                        stream_STRING.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "casecompare"


    public static class casedefault_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "casedefault"
    // TcSimple.g:356:1: casedefault : KEYWORD_DEFAULT COLON formula -> ^( KEYWORD_DEFAULT formula ) ;
    public final TcSimpleParser.casedefault_return casedefault() throws RecognitionException {
        TcSimpleParser.casedefault_return retval = new TcSimpleParser.casedefault_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token KEYWORD_DEFAULT292=null;
        Token COLON293=null;
        TcSimpleParser.formula_return formula294 =null;


        TcAst KEYWORD_DEFAULT292_tree=null;
        TcAst COLON293_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleTokenStream stream_KEYWORD_DEFAULT=new RewriteRuleTokenStream(adaptor,"token KEYWORD_DEFAULT");
        RewriteRuleSubtreeStream stream_formula=new RewriteRuleSubtreeStream(adaptor,"rule formula");
        try {
            // TcSimple.g:356:12: ( KEYWORD_DEFAULT COLON formula -> ^( KEYWORD_DEFAULT formula ) )
            // TcSimple.g:357:3: KEYWORD_DEFAULT COLON formula
            {
            KEYWORD_DEFAULT292=(Token)match(input,KEYWORD_DEFAULT,FOLLOW_KEYWORD_DEFAULT_in_casedefault5029);  
            stream_KEYWORD_DEFAULT.add(KEYWORD_DEFAULT292);


            COLON293=(Token)match(input,COLON,FOLLOW_COLON_in_casedefault5031);  
            stream_COLON.add(COLON293);


            pushFollow(FOLLOW_formula_in_casedefault5033);
            formula294=formula();

            state._fsp--;

            stream_formula.add(formula294.getTree());

            // AST REWRITE
            // elements: KEYWORD_DEFAULT, formula
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 357:38: -> ^( KEYWORD_DEFAULT formula )
            {
                // TcSimple.g:357:41: ^( KEYWORD_DEFAULT formula )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                stream_KEYWORD_DEFAULT.nextNode()
                , root_1);

                adaptor.addChild(root_1, stream_formula.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "casedefault"


    public static class caseconstant_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "caseconstant"
    // TcSimple.g:360:1: caseconstant : ( STRING | caseconstantnumber );
    public final TcSimpleParser.caseconstant_return caseconstant() throws RecognitionException {
        TcSimpleParser.caseconstant_return retval = new TcSimpleParser.caseconstant_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token STRING295=null;
        TcSimpleParser.caseconstantnumber_return caseconstantnumber296 =null;


        TcAst STRING295_tree=null;

        try {
            // TcSimple.g:360:13: ( STRING | caseconstantnumber )
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==STRING) ) {
                alt55=1;
            }
            else if ( (LA55_0==MINUS||LA55_0==NUMBER) ) {
                alt55=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;

            }
            switch (alt55) {
                case 1 :
                    // TcSimple.g:361:3: STRING
                    {
                    root_0 = (TcAst)adaptor.nil();


                    STRING295=(Token)match(input,STRING,FOLLOW_STRING_in_caseconstant5058); 
                    STRING295_tree = 
                    (TcAst)adaptor.create(STRING295)
                    ;
                    adaptor.addChild(root_0, STRING295_tree);


                    }
                    break;
                case 2 :
                    // TcSimple.g:362:3: caseconstantnumber
                    {
                    root_0 = (TcAst)adaptor.nil();


                    pushFollow(FOLLOW_caseconstantnumber_in_caseconstant5062);
                    caseconstantnumber296=caseconstantnumber();

                    state._fsp--;

                    adaptor.addChild(root_0, caseconstantnumber296.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "caseconstant"


    public static class caseconstantnumber_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "caseconstantnumber"
    // TcSimple.g:364:1: caseconstantnumber : ( NUMBER | MINUS n= NUMBER -> NUMBER[\"-\" + $n.getText()] );
    public final TcSimpleParser.caseconstantnumber_return caseconstantnumber() throws RecognitionException {
        TcSimpleParser.caseconstantnumber_return retval = new TcSimpleParser.caseconstantnumber_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token n=null;
        Token NUMBER297=null;
        Token MINUS298=null;

        TcAst n_tree=null;
        TcAst NUMBER297_tree=null;
        TcAst MINUS298_tree=null;
        RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");
        RewriteRuleTokenStream stream_NUMBER=new RewriteRuleTokenStream(adaptor,"token NUMBER");

        try {
            // TcSimple.g:364:19: ( NUMBER | MINUS n= NUMBER -> NUMBER[\"-\" + $n.getText()] )
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==NUMBER) ) {
                alt56=1;
            }
            else if ( (LA56_0==MINUS) ) {
                alt56=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                throw nvae;

            }
            switch (alt56) {
                case 1 :
                    // TcSimple.g:365:3: NUMBER
                    {
                    root_0 = (TcAst)adaptor.nil();


                    NUMBER297=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_caseconstantnumber5071); 
                    NUMBER297_tree = 
                    (TcAst)adaptor.create(NUMBER297)
                    ;
                    adaptor.addChild(root_0, NUMBER297_tree);


                    }
                    break;
                case 2 :
                    // TcSimple.g:366:3: MINUS n= NUMBER
                    {
                    MINUS298=(Token)match(input,MINUS,FOLLOW_MINUS_in_caseconstantnumber5075);  
                    stream_MINUS.add(MINUS298);


                    n=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_caseconstantnumber5079);  
                    stream_NUMBER.add(n);


                    // AST REWRITE
                    // elements: NUMBER
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 366:22: -> NUMBER[\"-\" + $n.getText()]
                    {
                        adaptor.addChild(root_0, 
                        (TcAst)adaptor.create(NUMBER, "-" + n.getText())
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "caseconstantnumber"


    public static class dyntable_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "dyntable"
    // TcSimple.g:369:1: dyntable : KEYWORD_TABREF PAREN_OPEN formula PAREN_CLOSE -> formula ;
    public final TcSimpleParser.dyntable_return dyntable() throws RecognitionException {
        TcSimpleParser.dyntable_return retval = new TcSimpleParser.dyntable_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token KEYWORD_TABREF299=null;
        Token PAREN_OPEN300=null;
        Token PAREN_CLOSE302=null;
        TcSimpleParser.formula_return formula301 =null;


        TcAst KEYWORD_TABREF299_tree=null;
        TcAst PAREN_OPEN300_tree=null;
        TcAst PAREN_CLOSE302_tree=null;
        RewriteRuleTokenStream stream_KEYWORD_TABREF=new RewriteRuleTokenStream(adaptor,"token KEYWORD_TABREF");
        RewriteRuleTokenStream stream_PAREN_OPEN=new RewriteRuleTokenStream(adaptor,"token PAREN_OPEN");
        RewriteRuleTokenStream stream_PAREN_CLOSE=new RewriteRuleTokenStream(adaptor,"token PAREN_CLOSE");
        RewriteRuleSubtreeStream stream_formula=new RewriteRuleSubtreeStream(adaptor,"rule formula");
        try {
            // TcSimple.g:369:9: ( KEYWORD_TABREF PAREN_OPEN formula PAREN_CLOSE -> formula )
            // TcSimple.g:369:11: KEYWORD_TABREF PAREN_OPEN formula PAREN_CLOSE
            {
            KEYWORD_TABREF299=(Token)match(input,KEYWORD_TABREF,FOLLOW_KEYWORD_TABREF_in_dyntable5097);  
            stream_KEYWORD_TABREF.add(KEYWORD_TABREF299);


            PAREN_OPEN300=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_dyntable5100);  
            stream_PAREN_OPEN.add(PAREN_OPEN300);


            pushFollow(FOLLOW_formula_in_dyntable5103);
            formula301=formula();

            state._fsp--;

            stream_formula.add(formula301.getTree());

            PAREN_CLOSE302=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_dyntable5106);  
            stream_PAREN_CLOSE.add(PAREN_CLOSE302);


            // AST REWRITE
            // elements: formula
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 369:60: -> formula
            {
                adaptor.addChild(root_0, stream_formula.nextTree());

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "dyntable"


    public static class columnaccess_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "columnaccess"
    // TcSimple.g:372:1: columnaccess : ( DOT id -> ^( TT_COLNAMESTATIC id ) | PAREN_OPEN formula PAREN_CLOSE -> ^( TT_COLNAMEFORMULA formula ) );
    public final TcSimpleParser.columnaccess_return columnaccess() throws RecognitionException {
        TcSimpleParser.columnaccess_return retval = new TcSimpleParser.columnaccess_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token DOT303=null;
        Token PAREN_OPEN305=null;
        Token PAREN_CLOSE307=null;
        TcSimpleParser.id_return id304 =null;

        TcSimpleParser.formula_return formula306 =null;


        TcAst DOT303_tree=null;
        TcAst PAREN_OPEN305_tree=null;
        TcAst PAREN_CLOSE307_tree=null;
        RewriteRuleTokenStream stream_PAREN_OPEN=new RewriteRuleTokenStream(adaptor,"token PAREN_OPEN");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_PAREN_CLOSE=new RewriteRuleTokenStream(adaptor,"token PAREN_CLOSE");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_formula=new RewriteRuleSubtreeStream(adaptor,"rule formula");
        try {
            // TcSimple.g:372:13: ( DOT id -> ^( TT_COLNAMESTATIC id ) | PAREN_OPEN formula PAREN_CLOSE -> ^( TT_COLNAMEFORMULA formula ) )
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==DOT) ) {
                alt57=1;
            }
            else if ( (LA57_0==PAREN_OPEN) ) {
                alt57=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;

            }
            switch (alt57) {
                case 1 :
                    // TcSimple.g:373:3: DOT id
                    {
                    DOT303=(Token)match(input,DOT,FOLLOW_DOT_in_columnaccess5121);  
                    stream_DOT.add(DOT303);


                    pushFollow(FOLLOW_id_in_columnaccess5123);
                    id304=id();

                    state._fsp--;

                    stream_id.add(id304.getTree());

                    // AST REWRITE
                    // elements: id
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 373:35: -> ^( TT_COLNAMESTATIC id )
                    {
                        // TcSimple.g:373:38: ^( TT_COLNAMESTATIC id )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        (TcAst)adaptor.create(TT_COLNAMESTATIC, "TT_COLNAMESTATIC")
                        , root_1);

                        adaptor.addChild(root_1, stream_id.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // TcSimple.g:374:3: PAREN_OPEN formula PAREN_CLOSE
                    {
                    PAREN_OPEN305=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_columnaccess5161);  
                    stream_PAREN_OPEN.add(PAREN_OPEN305);


                    pushFollow(FOLLOW_formula_in_columnaccess5163);
                    formula306=formula();

                    state._fsp--;

                    stream_formula.add(formula306.getTree());

                    PAREN_CLOSE307=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_columnaccess5165);  
                    stream_PAREN_CLOSE.add(PAREN_CLOSE307);


                    // AST REWRITE
                    // elements: formula
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (TcAst)adaptor.nil();
                    // 374:35: -> ^( TT_COLNAMEFORMULA formula )
                    {
                        // TcSimple.g:374:38: ^( TT_COLNAMEFORMULA formula )
                        {
                        TcAst root_1 = (TcAst)adaptor.nil();
                        root_1 = (TcAst)adaptor.becomeRoot(
                        (TcAst)adaptor.create(TT_COLNAMEFORMULA, "TT_COLNAMEFORMULA")
                        , root_1);

                        adaptor.addChild(root_1, stream_formula.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "columnaccess"


    public static class comparisonoperator_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "comparisonoperator"
    // TcSimple.g:377:1: comparisonoperator : ( COMPARE_EQUAL | COMPARE_EQUAL_CSTYLE | COMPARE_SMALLER | COMPARE_BIGGER | COMPARE_LESSEQUAL | COMPARE_BIGGEREQUAL | COMPARE_NOTEQUAL | COMPARE_NOTEQUAL_CSTYLE | COMPARE_STR_EQUAL | COMPARE_STR_NOTEQUAL | COMPARE_STR_ALIKE | COMPARE_STR_NOTALIKE | COMPARE_STR_BEFORE | COMPARE_STR_NOTBEFORE | COMPARE_STR_AHEAD | COMPARE_STR_NOTAHEAD | COMPARE_STR_BEHIND | COMPARE_STR_NOTBEHIND | COMPARE_STR_AFTER | COMPARE_STR_NOTAFTER );
    public final TcSimpleParser.comparisonoperator_return comparisonoperator() throws RecognitionException {
        TcSimpleParser.comparisonoperator_return retval = new TcSimpleParser.comparisonoperator_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token set308=null;

        TcAst set308_tree=null;

        try {
            // TcSimple.g:377:19: ( COMPARE_EQUAL | COMPARE_EQUAL_CSTYLE | COMPARE_SMALLER | COMPARE_BIGGER | COMPARE_LESSEQUAL | COMPARE_BIGGEREQUAL | COMPARE_NOTEQUAL | COMPARE_NOTEQUAL_CSTYLE | COMPARE_STR_EQUAL | COMPARE_STR_NOTEQUAL | COMPARE_STR_ALIKE | COMPARE_STR_NOTALIKE | COMPARE_STR_BEFORE | COMPARE_STR_NOTBEFORE | COMPARE_STR_AHEAD | COMPARE_STR_NOTAHEAD | COMPARE_STR_BEHIND | COMPARE_STR_NOTBEHIND | COMPARE_STR_AFTER | COMPARE_STR_NOTAFTER )
            // TcSimple.g:
            {
            root_0 = (TcAst)adaptor.nil();


            set308=(Token)input.LT(1);

            if ( (input.LA(1) >= COMPARE_BIGGER && input.LA(1) <= COMPARE_STR_NOTEQUAL) ) {
                input.consume();
                adaptor.addChild(root_0, 
                (TcAst)adaptor.create(set308)
                );
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "comparisonoperator"


    public static class parameterListe_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "parameterListe"
    // TcSimple.g:401:1: parameterListe : PAREN_OPEN ( formula ( COMMA formula )* )? PAREN_CLOSE -> ^( TT_PARAMETERS ( formula )* ) ;
    public final TcSimpleParser.parameterListe_return parameterListe() throws RecognitionException {
        TcSimpleParser.parameterListe_return retval = new TcSimpleParser.parameterListe_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token PAREN_OPEN309=null;
        Token COMMA311=null;
        Token PAREN_CLOSE313=null;
        TcSimpleParser.formula_return formula310 =null;

        TcSimpleParser.formula_return formula312 =null;


        TcAst PAREN_OPEN309_tree=null;
        TcAst COMMA311_tree=null;
        TcAst PAREN_CLOSE313_tree=null;
        RewriteRuleTokenStream stream_PAREN_OPEN=new RewriteRuleTokenStream(adaptor,"token PAREN_OPEN");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_PAREN_CLOSE=new RewriteRuleTokenStream(adaptor,"token PAREN_CLOSE");
        RewriteRuleSubtreeStream stream_formula=new RewriteRuleSubtreeStream(adaptor,"rule formula");
        try {
            // TcSimple.g:401:15: ( PAREN_OPEN ( formula ( COMMA formula )* )? PAREN_CLOSE -> ^( TT_PARAMETERS ( formula )* ) )
            // TcSimple.g:402:3: PAREN_OPEN ( formula ( COMMA formula )* )? PAREN_CLOSE
            {
            PAREN_OPEN309=(Token)match(input,PAREN_OPEN,FOLLOW_PAREN_OPEN_in_parameterListe5275);  
            stream_PAREN_OPEN.add(PAREN_OPEN309);


            // TcSimple.g:402:14: ( formula ( COMMA formula )* )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==ID||(LA59_0 >= KEYWORD_CASE && LA59_0 <= KEYWORD_COUNTERLIST)||LA59_0==KEYWORD_DOCALL||(LA59_0 >= KEYWORD_EXISTS && LA59_0 <= KEYWORD_EXTRACT)||(LA59_0 >= KEYWORD_FUNCREF && LA59_0 <= KEYWORD_IF)||LA59_0==KEYWORD_INTERPOL||(LA59_0 >= KEYWORD_LOOKDOWNX && LA59_0 <= KEYWORD_LOOKUPX)||(LA59_0 >= KEYWORD_PRODX && LA59_0 <= KEYWORD_TABCOLS)||(LA59_0 >= KEYWORD_TABREF && LA59_0 <= KEYWORD_TABROWS)||LA59_0==KEYWORD_VECTORX||LA59_0==MINUS||LA59_0==NUMBER||(LA59_0 >= PAREN_OPEN && LA59_0 <= PLUS)||LA59_0==STRING) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // TcSimple.g:402:15: formula ( COMMA formula )*
                    {
                    pushFollow(FOLLOW_formula_in_parameterListe5278);
                    formula310=formula();

                    state._fsp--;

                    stream_formula.add(formula310.getTree());

                    // TcSimple.g:402:24: ( COMMA formula )*
                    loop58:
                    do {
                        int alt58=2;
                        int LA58_0 = input.LA(1);

                        if ( (LA58_0==COMMA) ) {
                            alt58=1;
                        }


                        switch (alt58) {
                    	case 1 :
                    	    // TcSimple.g:402:25: COMMA formula
                    	    {
                    	    COMMA311=(Token)match(input,COMMA,FOLLOW_COMMA_in_parameterListe5282);  
                    	    stream_COMMA.add(COMMA311);


                    	    pushFollow(FOLLOW_formula_in_parameterListe5284);
                    	    formula312=formula();

                    	    state._fsp--;

                    	    stream_formula.add(formula312.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop58;
                        }
                    } while (true);


                    }
                    break;

            }


            PAREN_CLOSE313=(Token)match(input,PAREN_CLOSE,FOLLOW_PAREN_CLOSE_in_parameterListe5290);  
            stream_PAREN_CLOSE.add(PAREN_CLOSE313);


            // AST REWRITE
            // elements: formula
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 402:55: -> ^( TT_PARAMETERS ( formula )* )
            {
                // TcSimple.g:402:58: ^( TT_PARAMETERS ( formula )* )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                (TcAst)adaptor.create(TT_PARAMETERS, "TT_PARAMETERS")
                , root_1);

                // TcSimple.g:402:74: ( formula )*
                while ( stream_formula.hasNext() ) {
                    adaptor.addChild(root_1, stream_formula.nextTree());

                }
                stream_formula.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "parameterListe"


    public static class index_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "index"
    // TcSimple.g:405:1: index : BRACKET_OPEN formula ( COMMA formula )* BRACKET_CLOSE -> ^( TT_INDEX ( formula )* ) ;
    public final TcSimpleParser.index_return index() throws RecognitionException {
        TcSimpleParser.index_return retval = new TcSimpleParser.index_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token BRACKET_OPEN314=null;
        Token COMMA316=null;
        Token BRACKET_CLOSE318=null;
        TcSimpleParser.formula_return formula315 =null;

        TcSimpleParser.formula_return formula317 =null;


        TcAst BRACKET_OPEN314_tree=null;
        TcAst COMMA316_tree=null;
        TcAst BRACKET_CLOSE318_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_BRACKET_CLOSE=new RewriteRuleTokenStream(adaptor,"token BRACKET_CLOSE");
        RewriteRuleTokenStream stream_BRACKET_OPEN=new RewriteRuleTokenStream(adaptor,"token BRACKET_OPEN");
        RewriteRuleSubtreeStream stream_formula=new RewriteRuleSubtreeStream(adaptor,"rule formula");
        try {
            // TcSimple.g:405:6: ( BRACKET_OPEN formula ( COMMA formula )* BRACKET_CLOSE -> ^( TT_INDEX ( formula )* ) )
            // TcSimple.g:406:3: BRACKET_OPEN formula ( COMMA formula )* BRACKET_CLOSE
            {
            BRACKET_OPEN314=(Token)match(input,BRACKET_OPEN,FOLLOW_BRACKET_OPEN_in_index5383);  
            stream_BRACKET_OPEN.add(BRACKET_OPEN314);


            pushFollow(FOLLOW_formula_in_index5385);
            formula315=formula();

            state._fsp--;

            stream_formula.add(formula315.getTree());

            // TcSimple.g:406:24: ( COMMA formula )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==COMMA) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // TcSimple.g:406:25: COMMA formula
            	    {
            	    COMMA316=(Token)match(input,COMMA,FOLLOW_COMMA_in_index5388);  
            	    stream_COMMA.add(COMMA316);


            	    pushFollow(FOLLOW_formula_in_index5390);
            	    formula317=formula();

            	    state._fsp--;

            	    stream_formula.add(formula317.getTree());

            	    }
            	    break;

            	default :
            	    break loop60;
                }
            } while (true);


            BRACKET_CLOSE318=(Token)match(input,BRACKET_CLOSE,FOLLOW_BRACKET_CLOSE_in_index5394);  
            stream_BRACKET_CLOSE.add(BRACKET_CLOSE318);


            // AST REWRITE
            // elements: formula
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (TcAst)adaptor.nil();
            // 406:55: -> ^( TT_INDEX ( formula )* )
            {
                // TcSimple.g:406:58: ^( TT_INDEX ( formula )* )
                {
                TcAst root_1 = (TcAst)adaptor.nil();
                root_1 = (TcAst)adaptor.becomeRoot(
                (TcAst)adaptor.create(TT_INDEX, "TT_INDEX")
                , root_1);

                // TcSimple.g:406:69: ( formula )*
                while ( stream_formula.hasNext() ) {
                    adaptor.addChild(root_1, stream_formula.nextTree());

                }
                stream_formula.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "index"


    public static class constant_return extends ParserRuleReturnScope {
        TcAst tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "constant"
    // TcSimple.g:409:1: constant : ( STRING | NUMBER );
    public final TcSimpleParser.constant_return constant() throws RecognitionException {
        TcSimpleParser.constant_return retval = new TcSimpleParser.constant_return();
        retval.start = input.LT(1);


        TcAst root_0 = null;

        Token set319=null;

        TcAst set319_tree=null;

        try {
            // TcSimple.g:409:9: ( STRING | NUMBER )
            // TcSimple.g:
            {
            root_0 = (TcAst)adaptor.nil();


            set319=(Token)input.LT(1);

            if ( input.LA(1)==NUMBER||input.LA(1)==STRING ) {
                input.consume();
                adaptor.addChild(root_0, 
                (TcAst)adaptor.create(set319)
                );
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (TcAst)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (TcAst)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "constant"

    // Delegated rules


 

    public static final BitSet FOLLOW_def_in_compilationunit1291 = new BitSet(new long[]{0x0480040000000002L,0x0000000000000210L});
    public static final BitSet FOLLOW_KEYWORD_TREE_in_def1311 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_nodepath_in_def1313 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_CURLY_OPEN_in_def1315 = new BitSet(new long[]{0x1000000100000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_nodeinfo_in_def1317 = new BitSet(new long[]{0x1000000100000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_CURLY_CLOSE_in_def1320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_CALC_in_def1350 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_nodepath_in_def1352 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_CURLY_OPEN_in_def1354 = new BitSet(new long[]{0xEFFFFF0100000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_resultdef_in_def1356 = new BitSet(new long[]{0xEFFFFF0100000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_CURLY_CLOSE_in_def1359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_INPUT_in_def1389 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_id_in_def1391 = new BitSet(new long[]{0x0000000200000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_CURLY_OPEN_in_def1395 = new BitSet(new long[]{0xEFFFFF0100000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_resultdef_in_def1397 = new BitSet(new long[]{0xEFFFFF0100000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_CURLY_CLOSE_in_def1400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SEMICOLON_in_def1405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_FUNC_in_def1424 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_resultdef_in_def1426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_TABLE_in_def1485 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_id_in_def1487 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_def1489 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000008020EDFL});
    public static final BitSet FOLLOW_colnames_in_def1491 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_def1493 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_CURLY_OPEN_in_def1496 = new BitSet(new long[]{0xEFFFFF0100000000L,0x0000000008020EDFL});
    public static final BitSet FOLLOW_tableline_in_def1498 = new BitSet(new long[]{0xEFFFFF0100000000L,0x0000000008020EDFL});
    public static final BitSet FOLLOW_CURLY_CLOSE_in_def1501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_NODE_in_nodeinfo1638 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000008020EDFL});
    public static final BitSet FOLLOW_nodename_in_nodeinfo1640 = new BitSet(new long[]{0x0200020200000000L,0x0000000001000100L});
    public static final BitSet FOLLOW_KEYWORD_AS_in_nodeinfo1643 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_id_in_nodeinfo1645 = new BitSet(new long[]{0x0200000200000000L,0x0000000001000100L});
    public static final BitSet FOLLOW_nodeinclusion_in_nodeinfo1649 = new BitSet(new long[]{0x0000000200000000L,0x0000000001000100L});
    public static final BitSet FOLLOW_nodetimes_in_nodeinfo1652 = new BitSet(new long[]{0x0000000200000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_nodeinfo1656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CURLY_OPEN_in_nodeinfo1661 = new BitSet(new long[]{0x1000000100000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_nodeinfo_in_nodeinfo1663 = new BitSet(new long[]{0x1000000100000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_CURLY_CLOSE_in_nodeinfo1666 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_LINK_in_nodeinfo1702 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_linkpath_in_nodeinfo1704 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_nodeinfo1706 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_IF_in_nodeinclusion1802 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_nodeinclusion1804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_TIMES_in_nodetimes1879 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_nodetimes1881 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_KEYWORD_AS_in_nodetimes1883 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_id_in_nodetimes1885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_resultdef1974 = new BitSet(new long[]{0x0000000000004000L,0x0000000000100000L});
    public static final BitSet FOLLOW_arguments_in_resultdef1976 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_COMPARE_EQUAL_in_resultdef1980 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_resultdef1982 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_resultdef1984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_arguments2100 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_id_in_arguments2102 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_COMMA_in_arguments2105 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_id_in_arguments2107 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_arguments2111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_id2214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keywordAsId_in_id2238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_constantorid2495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_constantorid2499 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tablecell_in_tableline2568 = new BitSet(new long[]{0x0000000000000100L,0x0000000001000000L});
    public static final BitSet FOLLOW_COMMA_in_tableline2571 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000008020EDFL});
    public static final BitSet FOLLOW_tablecell_in_tableline2573 = new BitSet(new long[]{0x0000000000000100L,0x0000000001000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_tableline2577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_tablecell2641 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_tablecell2643 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_nodename2690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_nodename2692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_nodepath2713 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_DOT_in_nodepath2716 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_id_in_nodepath2718 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_id_in_linkpath2751 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_DOT_in_linkpath2754 = new BitSet(new long[]{0xEFFFFF2000000010L,0x0000000008000EDFL});
    public static final BitSet FOLLOW_linkpart_in_linkpath2756 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_id_in_linkpart2831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_linkpart2879 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ASTERISK_in_linkpart2893 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLEASTERISK_in_linkpart2905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_colname_in_colnames2920 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_colnames2923 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000008020EDFL});
    public static final BitSet FOLLOW_colname_in_colnames2925 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_id_in_colname3048 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_colname3054 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_colname3059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formula_in_vpmsformula3114 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_vpmsformula3116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formula2_in_formula3124 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_QUESTIONMARK_in_formula3127 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula2_in_formula3130 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_COLON_in_formula3132 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula2_in_formula3135 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_formula3_in_formula23143 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_LOGICAL_OR_in_formula23146 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula3_in_formula23149 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_formula4_in_formula33157 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_LOGICAL_AND_in_formula33160 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula4_in_formula33163 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_formula5_in_formula43171 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_LOGICAL_XOR_in_formula43174 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula5_in_formula43177 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_formula6_in_formula53185 = new BitSet(new long[]{0x00000000FFFFF002L});
    public static final BitSet FOLLOW_comparisonoperator_in_formula53188 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula6_in_formula53191 = new BitSet(new long[]{0x00000000FFFFF002L});
    public static final BitSet FOLLOW_formula7_in_formula63199 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_STRCAT_in_formula63202 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula7_in_formula63205 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_formula8_in_formula73213 = new BitSet(new long[]{0x0000000000000002L,0x0000000000208000L});
    public static final BitSet FOLLOW_set_in_formula73216 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula8_in_formula73223 = new BitSet(new long[]{0x0000000000000002L,0x0000000000208000L});
    public static final BitSet FOLLOW_formula9_in_formula83231 = new BitSet(new long[]{0x0000000400000012L,0x0000000002010000L});
    public static final BitSet FOLLOW_set_in_formula83234 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula9_in_formula83245 = new BitSet(new long[]{0x0000000400000012L,0x0000000002010000L});
    public static final BitSet FOLLOW_formula10_in_formula93253 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_POWER_in_formula93256 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula9_in_formula93259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_in_formula103268 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_MINUS_in_formula103271 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_expression_in_formula103276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3285 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3288 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_SUMX_in_expression3295 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3298 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_id_in_expression3300 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3302 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3304 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3306 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3308 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3310 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3312 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_PRODX_in_expression3339 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3341 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_id_in_expression3343 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3345 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3347 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3349 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3351 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3353 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3355 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_VECTORX_in_expression3382 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3384 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_id_in_expression3386 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3388 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3390 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3392 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3394 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3396 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3398 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_COLLATE_in_expression3425 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3427 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_id_in_expression3429 = new BitSet(new long[]{0x0000000000000000L,0x0000000000180000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3432 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3434 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_COMMA_in_expression3437 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3439 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3443 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_EXTRACT_in_expression3472 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3474 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_id_in_expression3476 = new BitSet(new long[]{0x0000000000000100L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3479 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3481 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_COMMA_in_expression3484 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3486 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3490 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3494 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3496 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_CELL_in_expression3523 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3525 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EFFL});
    public static final BitSet FOLLOW_tableref_in_expression3527 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3529 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_range_in_expression3531 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3533 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_range_in_expression3535 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3537 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_CELLX_in_expression3562 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3564 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EFFL});
    public static final BitSet FOLLOW_tableref_in_expression3566 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3568 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_range_in_expression3570 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3572 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_range_in_expression3574 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_LOOKUP_in_expression3601 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3603 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EFFL});
    public static final BitSet FOLLOW_tableref_in_expression3605 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3607 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3609 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3611 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_LOOKUPX_in_expression3636 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3638 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EFFL});
    public static final BitSet FOLLOW_tableref_in_expression3640 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3642 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3644 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_COMMA_in_expression3647 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3649 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3653 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_LOOKDOWNX_in_expression3678 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3680 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EFFL});
    public static final BitSet FOLLOW_tableref_in_expression3682 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3684 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3686 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_COMMA_in_expression3689 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3691 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3695 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_EXISTS_in_expression3720 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3722 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EFFL});
    public static final BitSet FOLLOW_tableref_in_expression3724 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3726 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3728 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_COMMA_in_expression3732 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3734 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_INTERPOL_in_expression3763 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3765 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EFFL});
    public static final BitSet FOLLOW_tableref_in_expression3767 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_expression3769 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3771 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_TABCOLS_in_expression3797 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3799 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EFFL});
    public static final BitSet FOLLOW_tableref_in_expression3801 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3803 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_TABROWS_in_expression3825 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3827 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EFFL});
    public static final BitSet FOLLOW_tableref_in_expression3829 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_FUNCREF_in_expression3853 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3855 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3857 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_DOCALL_in_expression3881 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3883 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3885 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_COMMA_in_expression3888 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_expression3890 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3894 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_COUNTERLIST_in_expression3917 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_expression3919 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_id_in_expression3921 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_COMMA_in_expression3924 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_id_in_expression3926 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_expression3930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_expression3953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_expression3996 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_DOT_in_expression3999 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_id_in_expression4002 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_expression4027 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_index_in_expression4030 = new BitSet(new long[]{0x0000000800000002L,0x0000000000100000L});
    public static final BitSet FOLLOW_columnaccess_in_expression4032 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dyntable_in_expression4209 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_index_in_expression4212 = new BitSet(new long[]{0x0000000800000002L,0x0000000000100000L});
    public static final BitSet FOLLOW_columnaccess_in_expression4214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_expression4288 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_parameterListe_in_expression4291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifstmt_in_expression4324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_casestmt_in_expression4328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_expression4332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_tableref4413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dyntable_in_tableref4417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formula_in_range4492 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_DOTS_in_range4495 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_range4497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_IF_in_ifstmt4526 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_ifstmt4528 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_KEYWORD_THEN_in_ifstmt4530 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_ifstmt4532 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_KEYWORD_ELSE_in_ifstmt4534 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_ifstmt4536 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_KEYWORD_ENDIF_in_ifstmt4538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_CASE_in_casestmt4616 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_casestmt4618 = new BitSet(new long[]{0x0009000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_casewhen_in_casestmt4620 = new BitSet(new long[]{0x0009000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_casedefault_in_casestmt4623 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_KEYWORD_ENDCASE_in_casestmt4626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_WHEN_in_casewhen4767 = new BitSet(new long[]{0x0000000000000000L,0x0000000008028000L});
    public static final BitSet FOLLOW_casecompares_in_casewhen4769 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_COLON_in_casewhen4771 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_casewhen4773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_casecompare_in_casecompares4800 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_casecompares4803 = new BitSet(new long[]{0x0000000000000000L,0x0000000008028000L});
    public static final BitSet FOLLOW_casecompare_in_casecompares4805 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_caseconstant_in_casecompare4830 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseconstantnumber_in_casecompare4872 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOTS_in_casecompare4874 = new BitSet(new long[]{0x0000000000000000L,0x0000000000028000L});
    public static final BitSet FOLLOW_caseconstantnumber_in_casecompare4876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_casecompare4894 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_DOTS_in_casecompare4896 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_STRING_in_casecompare4898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_DEFAULT_in_casedefault5029 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_COLON_in_casedefault5031 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_casedefault5033 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_caseconstant5058 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseconstantnumber_in_caseconstant5062 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_caseconstantnumber5071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_caseconstantnumber5075 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_NUMBER_in_caseconstantnumber5079 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KEYWORD_TABREF_in_dyntable5097 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_dyntable5100 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_dyntable5103 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_dyntable5106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_columnaccess5121 = new BitSet(new long[]{0xEFFFFF0000000000L,0x0000000000000EDFL});
    public static final BitSet FOLLOW_id_in_columnaccess5123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_columnaccess5161 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_columnaccess5163 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_columnaccess5165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PAREN_OPEN_in_parameterListe5275 = new BitSet(new long[]{0xEB62F90000000000L,0x00000000083A846EL});
    public static final BitSet FOLLOW_formula_in_parameterListe5278 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_COMMA_in_parameterListe5282 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_parameterListe5284 = new BitSet(new long[]{0x0000000000000100L,0x0000000000080000L});
    public static final BitSet FOLLOW_PAREN_CLOSE_in_parameterListe5290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BRACKET_OPEN_in_index5383 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_index5385 = new BitSet(new long[]{0x0000000000000120L});
    public static final BitSet FOLLOW_COMMA_in_index5388 = new BitSet(new long[]{0xEB62F90000000000L,0x000000000832846EL});
    public static final BitSet FOLLOW_formula_in_index5390 = new BitSet(new long[]{0x0000000000000120L});
    public static final BitSet FOLLOW_BRACKET_CLOSE_in_index5394 = new BitSet(new long[]{0x0000000000000002L});

}