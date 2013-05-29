// $ANTLR 3.4 TcSimple.g 2011-12-06 13:21:40
package treecalc.comp; 

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class TcSimpleLexer extends Lexer {
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
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public TcSimpleLexer() {} 
    public TcSimpleLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public TcSimpleLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "TcSimple.g"; }

    // $ANTLR start "ASTERISK"
    public final void mASTERISK() throws RecognitionException {
        try {
            int _type = ASTERISK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:4:10: ( '*' )
            // TcSimple.g:4:12: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ASTERISK"

    // $ANTLR start "BRACKET_CLOSE"
    public final void mBRACKET_CLOSE() throws RecognitionException {
        try {
            int _type = BRACKET_CLOSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:5:15: ( ']' )
            // TcSimple.g:5:17: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "BRACKET_CLOSE"

    // $ANTLR start "BRACKET_OPEN"
    public final void mBRACKET_OPEN() throws RecognitionException {
        try {
            int _type = BRACKET_OPEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:6:14: ( '[' )
            // TcSimple.g:6:16: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "BRACKET_OPEN"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:7:7: ( ':' )
            // TcSimple.g:7:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:8:7: ( ',' )
            // TcSimple.g:8:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "COMPARE_BIGGER"
    public final void mCOMPARE_BIGGER() throws RecognitionException {
        try {
            int _type = COMPARE_BIGGER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:9:16: ( '>' )
            // TcSimple.g:9:18: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_BIGGER"

    // $ANTLR start "COMPARE_BIGGEREQUAL"
    public final void mCOMPARE_BIGGEREQUAL() throws RecognitionException {
        try {
            int _type = COMPARE_BIGGEREQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:10:21: ( '>=' )
            // TcSimple.g:10:23: '>='
            {
            match(">="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_BIGGEREQUAL"

    // $ANTLR start "COMPARE_EQUAL"
    public final void mCOMPARE_EQUAL() throws RecognitionException {
        try {
            int _type = COMPARE_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:11:15: ( '=' )
            // TcSimple.g:11:17: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_EQUAL"

    // $ANTLR start "COMPARE_EQUAL_CSTYLE"
    public final void mCOMPARE_EQUAL_CSTYLE() throws RecognitionException {
        try {
            int _type = COMPARE_EQUAL_CSTYLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:12:22: ( '==' )
            // TcSimple.g:12:24: '=='
            {
            match("=="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_EQUAL_CSTYLE"

    // $ANTLR start "COMPARE_LESSEQUAL"
    public final void mCOMPARE_LESSEQUAL() throws RecognitionException {
        try {
            int _type = COMPARE_LESSEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:13:19: ( '<=' )
            // TcSimple.g:13:21: '<='
            {
            match("<="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_LESSEQUAL"

    // $ANTLR start "COMPARE_NOTEQUAL"
    public final void mCOMPARE_NOTEQUAL() throws RecognitionException {
        try {
            int _type = COMPARE_NOTEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:14:18: ( '<>' )
            // TcSimple.g:14:20: '<>'
            {
            match("<>"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_NOTEQUAL"

    // $ANTLR start "COMPARE_NOTEQUAL_CSTYLE"
    public final void mCOMPARE_NOTEQUAL_CSTYLE() throws RecognitionException {
        try {
            int _type = COMPARE_NOTEQUAL_CSTYLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:15:25: ( '!=' )
            // TcSimple.g:15:27: '!='
            {
            match("!="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_NOTEQUAL_CSTYLE"

    // $ANTLR start "COMPARE_SMALLER"
    public final void mCOMPARE_SMALLER() throws RecognitionException {
        try {
            int _type = COMPARE_SMALLER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:16:17: ( '<' )
            // TcSimple.g:16:19: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_SMALLER"

    // $ANTLR start "COMPARE_STR_AFTER"
    public final void mCOMPARE_STR_AFTER() throws RecognitionException {
        try {
            int _type = COMPARE_STR_AFTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:17:19: ( 'si>' )
            // TcSimple.g:17:21: 'si>'
            {
            match("si>"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_STR_AFTER"

    // $ANTLR start "COMPARE_STR_AHEAD"
    public final void mCOMPARE_STR_AHEAD() throws RecognitionException {
        try {
            int _type = COMPARE_STR_AHEAD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:18:19: ( 'si<' )
            // TcSimple.g:18:21: 'si<'
            {
            match("si<"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_STR_AHEAD"

    // $ANTLR start "COMPARE_STR_ALIKE"
    public final void mCOMPARE_STR_ALIKE() throws RecognitionException {
        try {
            int _type = COMPARE_STR_ALIKE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:19:19: ( 'si=' )
            // TcSimple.g:19:21: 'si='
            {
            match("si="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_STR_ALIKE"

    // $ANTLR start "COMPARE_STR_BEFORE"
    public final void mCOMPARE_STR_BEFORE() throws RecognitionException {
        try {
            int _type = COMPARE_STR_BEFORE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:20:20: ( 's<' )
            // TcSimple.g:20:22: 's<'
            {
            match("s<"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_STR_BEFORE"

    // $ANTLR start "COMPARE_STR_BEHIND"
    public final void mCOMPARE_STR_BEHIND() throws RecognitionException {
        try {
            int _type = COMPARE_STR_BEHIND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:21:20: ( 's>' )
            // TcSimple.g:21:22: 's>'
            {
            match("s>"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_STR_BEHIND"

    // $ANTLR start "COMPARE_STR_EQUAL"
    public final void mCOMPARE_STR_EQUAL() throws RecognitionException {
        try {
            int _type = COMPARE_STR_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:22:19: ( 's=' )
            // TcSimple.g:22:21: 's='
            {
            match("s="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_STR_EQUAL"

    // $ANTLR start "COMPARE_STR_NOTAFTER"
    public final void mCOMPARE_STR_NOTAFTER() throws RecognitionException {
        try {
            int _type = COMPARE_STR_NOTAFTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:23:22: ( 'si<=' )
            // TcSimple.g:23:24: 'si<='
            {
            match("si<="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_STR_NOTAFTER"

    // $ANTLR start "COMPARE_STR_NOTAHEAD"
    public final void mCOMPARE_STR_NOTAHEAD() throws RecognitionException {
        try {
            int _type = COMPARE_STR_NOTAHEAD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:24:22: ( 'si>=' )
            // TcSimple.g:24:24: 'si>='
            {
            match("si>="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_STR_NOTAHEAD"

    // $ANTLR start "COMPARE_STR_NOTALIKE"
    public final void mCOMPARE_STR_NOTALIKE() throws RecognitionException {
        try {
            int _type = COMPARE_STR_NOTALIKE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:25:22: ( 'si<>' )
            // TcSimple.g:25:24: 'si<>'
            {
            match("si<>"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_STR_NOTALIKE"

    // $ANTLR start "COMPARE_STR_NOTBEFORE"
    public final void mCOMPARE_STR_NOTBEFORE() throws RecognitionException {
        try {
            int _type = COMPARE_STR_NOTBEFORE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:26:23: ( 's>=' )
            // TcSimple.g:26:25: 's>='
            {
            match("s>="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_STR_NOTBEFORE"

    // $ANTLR start "COMPARE_STR_NOTBEHIND"
    public final void mCOMPARE_STR_NOTBEHIND() throws RecognitionException {
        try {
            int _type = COMPARE_STR_NOTBEHIND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:27:23: ( 's<=' )
            // TcSimple.g:27:25: 's<='
            {
            match("s<="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_STR_NOTBEHIND"

    // $ANTLR start "COMPARE_STR_NOTEQUAL"
    public final void mCOMPARE_STR_NOTEQUAL() throws RecognitionException {
        try {
            int _type = COMPARE_STR_NOTEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:28:22: ( 's<>' )
            // TcSimple.g:28:24: 's<>'
            {
            match("s<>"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMPARE_STR_NOTEQUAL"

    // $ANTLR start "CURLY_CLOSE"
    public final void mCURLY_CLOSE() throws RecognitionException {
        try {
            int _type = CURLY_CLOSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:29:13: ( '}' )
            // TcSimple.g:29:15: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CURLY_CLOSE"

    // $ANTLR start "CURLY_OPEN"
    public final void mCURLY_OPEN() throws RecognitionException {
        try {
            int _type = CURLY_OPEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:30:12: ( '{' )
            // TcSimple.g:30:14: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CURLY_OPEN"

    // $ANTLR start "DIV"
    public final void mDIV() throws RecognitionException {
        try {
            int _type = DIV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:31:5: ( 'DIV' )
            // TcSimple.g:31:7: 'DIV'
            {
            match("DIV"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIV"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:32:5: ( '.' )
            // TcSimple.g:32:7: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "DOTS"
    public final void mDOTS() throws RecognitionException {
        try {
            int _type = DOTS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:33:6: ( '..' )
            // TcSimple.g:33:8: '..'
            {
            match(".."); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DOTS"

    // $ANTLR start "DOUBLEASTERISK"
    public final void mDOUBLEASTERISK() throws RecognitionException {
        try {
            int _type = DOUBLEASTERISK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:34:16: ( '**' )
            // TcSimple.g:34:18: '**'
            {
            match("**"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DOUBLEASTERISK"

    // $ANTLR start "KEYWORD_AS"
    public final void mKEYWORD_AS() throws RecognitionException {
        try {
            int _type = KEYWORD_AS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:35:12: ( 'AS' )
            // TcSimple.g:35:14: 'AS'
            {
            match("AS"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_AS"

    // $ANTLR start "KEYWORD_CALC"
    public final void mKEYWORD_CALC() throws RecognitionException {
        try {
            int _type = KEYWORD_CALC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:36:14: ( 'CALC' )
            // TcSimple.g:36:16: 'CALC'
            {
            match("CALC"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_CALC"

    // $ANTLR start "KEYWORD_CASE"
    public final void mKEYWORD_CASE() throws RecognitionException {
        try {
            int _type = KEYWORD_CASE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:37:14: ( 'CASE' )
            // TcSimple.g:37:16: 'CASE'
            {
            match("CASE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_CASE"

    // $ANTLR start "KEYWORD_CELL"
    public final void mKEYWORD_CELL() throws RecognitionException {
        try {
            int _type = KEYWORD_CELL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:38:14: ( 'cell' )
            // TcSimple.g:38:16: 'cell'
            {
            match("cell"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_CELL"

    // $ANTLR start "KEYWORD_CELLX"
    public final void mKEYWORD_CELLX() throws RecognitionException {
        try {
            int _type = KEYWORD_CELLX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:39:15: ( 'cellx' )
            // TcSimple.g:39:17: 'cellx'
            {
            match("cellx"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_CELLX"

    // $ANTLR start "KEYWORD_COLLATE"
    public final void mKEYWORD_COLLATE() throws RecognitionException {
        try {
            int _type = KEYWORD_COLLATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:40:17: ( 'collate' )
            // TcSimple.g:40:19: 'collate'
            {
            match("collate"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_COLLATE"

    // $ANTLR start "KEYWORD_COUNTERLIST"
    public final void mKEYWORD_COUNTERLIST() throws RecognitionException {
        try {
            int _type = KEYWORD_COUNTERLIST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:41:21: ( 'counterlist' )
            // TcSimple.g:41:23: 'counterlist'
            {
            match("counterlist"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_COUNTERLIST"

    // $ANTLR start "KEYWORD_DEFAULT"
    public final void mKEYWORD_DEFAULT() throws RecognitionException {
        try {
            int _type = KEYWORD_DEFAULT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:42:17: ( 'DEFAULT' )
            // TcSimple.g:42:19: 'DEFAULT'
            {
            match("DEFAULT"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_DEFAULT"

    // $ANTLR start "KEYWORD_DOCALL"
    public final void mKEYWORD_DOCALL() throws RecognitionException {
        try {
            int _type = KEYWORD_DOCALL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:43:16: ( 'docall' )
            // TcSimple.g:43:18: 'docall'
            {
            match("docall"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_DOCALL"

    // $ANTLR start "KEYWORD_ELSE"
    public final void mKEYWORD_ELSE() throws RecognitionException {
        try {
            int _type = KEYWORD_ELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:44:14: ( 'ELSE' )
            // TcSimple.g:44:16: 'ELSE'
            {
            match("ELSE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_ELSE"

    // $ANTLR start "KEYWORD_ENDCASE"
    public final void mKEYWORD_ENDCASE() throws RecognitionException {
        try {
            int _type = KEYWORD_ENDCASE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:45:17: ( 'ENDCASE' )
            // TcSimple.g:45:19: 'ENDCASE'
            {
            match("ENDCASE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_ENDCASE"

    // $ANTLR start "KEYWORD_ENDIF"
    public final void mKEYWORD_ENDIF() throws RecognitionException {
        try {
            int _type = KEYWORD_ENDIF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:46:15: ( 'ENDIF' )
            // TcSimple.g:46:17: 'ENDIF'
            {
            match("ENDIF"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_ENDIF"

    // $ANTLR start "KEYWORD_EXISTS"
    public final void mKEYWORD_EXISTS() throws RecognitionException {
        try {
            int _type = KEYWORD_EXISTS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:47:16: ( 'exists' )
            // TcSimple.g:47:18: 'exists'
            {
            match("exists"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_EXISTS"

    // $ANTLR start "KEYWORD_EXTRACT"
    public final void mKEYWORD_EXTRACT() throws RecognitionException {
        try {
            int _type = KEYWORD_EXTRACT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:48:17: ( 'extract' )
            // TcSimple.g:48:19: 'extract'
            {
            match("extract"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_EXTRACT"

    // $ANTLR start "KEYWORD_FUNC"
    public final void mKEYWORD_FUNC() throws RecognitionException {
        try {
            int _type = KEYWORD_FUNC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:49:14: ( 'FUNC' )
            // TcSimple.g:49:16: 'FUNC'
            {
            match("FUNC"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_FUNC"

    // $ANTLR start "KEYWORD_FUNCREF"
    public final void mKEYWORD_FUNCREF() throws RecognitionException {
        try {
            int _type = KEYWORD_FUNCREF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:50:17: ( 'funcref' )
            // TcSimple.g:50:19: 'funcref'
            {
            match("funcref"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_FUNCREF"

    // $ANTLR start "KEYWORD_IF"
    public final void mKEYWORD_IF() throws RecognitionException {
        try {
            int _type = KEYWORD_IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:51:12: ( 'IF' )
            // TcSimple.g:51:14: 'IF'
            {
            match("IF"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_IF"

    // $ANTLR start "KEYWORD_INPUT"
    public final void mKEYWORD_INPUT() throws RecognitionException {
        try {
            int _type = KEYWORD_INPUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:52:15: ( 'INPUT' )
            // TcSimple.g:52:17: 'INPUT'
            {
            match("INPUT"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_INPUT"

    // $ANTLR start "KEYWORD_INTERPOL"
    public final void mKEYWORD_INTERPOL() throws RecognitionException {
        try {
            int _type = KEYWORD_INTERPOL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:53:18: ( 'interpol' )
            // TcSimple.g:53:20: 'interpol'
            {
            match("interpol"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_INTERPOL"

    // $ANTLR start "KEYWORD_LINK"
    public final void mKEYWORD_LINK() throws RecognitionException {
        try {
            int _type = KEYWORD_LINK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:54:14: ( 'LINK' )
            // TcSimple.g:54:16: 'LINK'
            {
            match("LINK"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_LINK"

    // $ANTLR start "KEYWORD_LOOKDOWNX"
    public final void mKEYWORD_LOOKDOWNX() throws RecognitionException {
        try {
            int _type = KEYWORD_LOOKDOWNX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:55:19: ( 'lookdownx' )
            // TcSimple.g:55:21: 'lookdownx'
            {
            match("lookdownx"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_LOOKDOWNX"

    // $ANTLR start "KEYWORD_LOOKUP"
    public final void mKEYWORD_LOOKUP() throws RecognitionException {
        try {
            int _type = KEYWORD_LOOKUP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:56:16: ( 'lookup' )
            // TcSimple.g:56:18: 'lookup'
            {
            match("lookup"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_LOOKUP"

    // $ANTLR start "KEYWORD_LOOKUPX"
    public final void mKEYWORD_LOOKUPX() throws RecognitionException {
        try {
            int _type = KEYWORD_LOOKUPX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:57:17: ( 'lookupx' )
            // TcSimple.g:57:19: 'lookupx'
            {
            match("lookupx"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_LOOKUPX"

    // $ANTLR start "KEYWORD_NODE"
    public final void mKEYWORD_NODE() throws RecognitionException {
        try {
            int _type = KEYWORD_NODE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:58:14: ( 'NODE' )
            // TcSimple.g:58:16: 'NODE'
            {
            match("NODE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_NODE"

    // $ANTLR start "KEYWORD_PRODX"
    public final void mKEYWORD_PRODX() throws RecognitionException {
        try {
            int _type = KEYWORD_PRODX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:59:15: ( 'prodx' )
            // TcSimple.g:59:17: 'prodx'
            {
            match("prodx"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_PRODX"

    // $ANTLR start "KEYWORD_SUMX"
    public final void mKEYWORD_SUMX() throws RecognitionException {
        try {
            int _type = KEYWORD_SUMX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:60:14: ( 'sumx' )
            // TcSimple.g:60:16: 'sumx'
            {
            match("sumx"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_SUMX"

    // $ANTLR start "KEYWORD_TABCOLS"
    public final void mKEYWORD_TABCOLS() throws RecognitionException {
        try {
            int _type = KEYWORD_TABCOLS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:61:17: ( 'tabcols' )
            // TcSimple.g:61:19: 'tabcols'
            {
            match("tabcols"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_TABCOLS"

    // $ANTLR start "KEYWORD_TABLE"
    public final void mKEYWORD_TABLE() throws RecognitionException {
        try {
            int _type = KEYWORD_TABLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:62:15: ( 'TABLE' )
            // TcSimple.g:62:17: 'TABLE'
            {
            match("TABLE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_TABLE"

    // $ANTLR start "KEYWORD_TABREF"
    public final void mKEYWORD_TABREF() throws RecognitionException {
        try {
            int _type = KEYWORD_TABREF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:63:16: ( 'tabref' )
            // TcSimple.g:63:18: 'tabref'
            {
            match("tabref"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_TABREF"

    // $ANTLR start "KEYWORD_TABROWS"
    public final void mKEYWORD_TABROWS() throws RecognitionException {
        try {
            int _type = KEYWORD_TABROWS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:64:17: ( 'tabrows' )
            // TcSimple.g:64:19: 'tabrows'
            {
            match("tabrows"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_TABROWS"

    // $ANTLR start "KEYWORD_THEN"
    public final void mKEYWORD_THEN() throws RecognitionException {
        try {
            int _type = KEYWORD_THEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:65:14: ( 'THEN' )
            // TcSimple.g:65:16: 'THEN'
            {
            match("THEN"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_THEN"

    // $ANTLR start "KEYWORD_TIMES"
    public final void mKEYWORD_TIMES() throws RecognitionException {
        try {
            int _type = KEYWORD_TIMES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:66:15: ( 'TIMES' )
            // TcSimple.g:66:17: 'TIMES'
            {
            match("TIMES"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_TIMES"

    // $ANTLR start "KEYWORD_TREE"
    public final void mKEYWORD_TREE() throws RecognitionException {
        try {
            int _type = KEYWORD_TREE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:67:14: ( 'TREE' )
            // TcSimple.g:67:16: 'TREE'
            {
            match("TREE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_TREE"

    // $ANTLR start "KEYWORD_VECTORX"
    public final void mKEYWORD_VECTORX() throws RecognitionException {
        try {
            int _type = KEYWORD_VECTORX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:68:17: ( 'vectorx' )
            // TcSimple.g:68:19: 'vectorx'
            {
            match("vectorx"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_VECTORX"

    // $ANTLR start "KEYWORD_WHEN"
    public final void mKEYWORD_WHEN() throws RecognitionException {
        try {
            int _type = KEYWORD_WHEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:69:14: ( 'WHEN' )
            // TcSimple.g:69:16: 'WHEN'
            {
            match("WHEN"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KEYWORD_WHEN"

    // $ANTLR start "LOGICAL_AND"
    public final void mLOGICAL_AND() throws RecognitionException {
        try {
            int _type = LOGICAL_AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:70:13: ( '&&' )
            // TcSimple.g:70:15: '&&'
            {
            match("&&"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LOGICAL_AND"

    // $ANTLR start "LOGICAL_OR"
    public final void mLOGICAL_OR() throws RecognitionException {
        try {
            int _type = LOGICAL_OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:71:12: ( '||' )
            // TcSimple.g:71:14: '||'
            {
            match("||"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LOGICAL_OR"

    // $ANTLR start "LOGICAL_XOR"
    public final void mLOGICAL_XOR() throws RecognitionException {
        try {
            int _type = LOGICAL_XOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:72:13: ( 'XOR' )
            // TcSimple.g:72:15: 'XOR'
            {
            match("XOR"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LOGICAL_XOR"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:73:7: ( '-' )
            // TcSimple.g:73:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "MOD"
    public final void mMOD() throws RecognitionException {
        try {
            int _type = MOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:74:5: ( 'MOD' )
            // TcSimple.g:74:7: 'MOD'
            {
            match("MOD"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MOD"

    // $ANTLR start "PAREN_CLOSE"
    public final void mPAREN_CLOSE() throws RecognitionException {
        try {
            int _type = PAREN_CLOSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:75:13: ( ')' )
            // TcSimple.g:75:15: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PAREN_CLOSE"

    // $ANTLR start "PAREN_OPEN"
    public final void mPAREN_OPEN() throws RecognitionException {
        try {
            int _type = PAREN_OPEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:76:12: ( '(' )
            // TcSimple.g:76:14: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PAREN_OPEN"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:77:6: ( '+' )
            // TcSimple.g:77:8: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "POWER"
    public final void mPOWER() throws RecognitionException {
        try {
            int _type = POWER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:78:7: ( '^' )
            // TcSimple.g:78:9: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "POWER"

    // $ANTLR start "QUESTIONMARK"
    public final void mQUESTIONMARK() throws RecognitionException {
        try {
            int _type = QUESTIONMARK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:79:14: ( '?' )
            // TcSimple.g:79:16: '?'
            {
            match('?'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "QUESTIONMARK"

    // $ANTLR start "SEMICOLON"
    public final void mSEMICOLON() throws RecognitionException {
        try {
            int _type = SEMICOLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:80:11: ( ';' )
            // TcSimple.g:80:13: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SEMICOLON"

    // $ANTLR start "SLASH"
    public final void mSLASH() throws RecognitionException {
        try {
            int _type = SLASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:81:7: ( '/' )
            // TcSimple.g:81:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SLASH"

    // $ANTLR start "STRCAT"
    public final void mSTRCAT() throws RecognitionException {
        try {
            int _type = STRCAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:82:8: ( '&' )
            // TcSimple.g:82:10: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRCAT"

    // $ANTLR start "NUMBER"
    public final void mNUMBER() throws RecognitionException {
        try {
            int _type = NUMBER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:414:8: ( NUMBER_INT | NUMBER_INT EXPONENT | NUMBER_INT '.' NUMBER_INT ( EXPONENT )? )
            int alt2=3;
            alt2 = dfa2.predict(input);
            switch (alt2) {
                case 1 :
                    // TcSimple.g:415:3: NUMBER_INT
                    {
                    mNUMBER_INT(); 


                    }
                    break;
                case 2 :
                    // TcSimple.g:416:3: NUMBER_INT EXPONENT
                    {
                    mNUMBER_INT(); 


                    mEXPONENT(); 


                    }
                    break;
                case 3 :
                    // TcSimple.g:418:3: NUMBER_INT '.' NUMBER_INT ( EXPONENT )?
                    {
                    mNUMBER_INT(); 


                    match('.'); 

                    mNUMBER_INT(); 


                    // TcSimple.g:418:29: ( EXPONENT )?
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0=='E'||LA1_0=='e') ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            // TcSimple.g:418:29: EXPONENT
                            {
                            mEXPONENT(); 


                            }
                            break;

                    }


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NUMBER"

    // $ANTLR start "NUMBER_INT"
    public final void mNUMBER_INT() throws RecognitionException {
        try {
            // TcSimple.g:421:20: ( ( '0' .. '9' )+ )
            // TcSimple.g:421:22: ( '0' .. '9' )+
            {
            // TcSimple.g:421:22: ( '0' .. '9' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0 >= '0' && LA3_0 <= '9')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // TcSimple.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NUMBER_INT"

    // $ANTLR start "EXPONENT"
    public final void mEXPONENT() throws RecognitionException {
        try {
            // TcSimple.g:422:18: ( ( 'e' | 'E' ) ( '-' )? ( '0' .. '9' )+ )
            // TcSimple.g:422:20: ( 'e' | 'E' ) ( '-' )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // TcSimple.g:422:30: ( '-' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='-') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // TcSimple.g:422:30: '-'
                    {
                    match('-'); 

                    }
                    break;

            }


            // TcSimple.g:422:35: ( '0' .. '9' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0 >= '0' && LA5_0 <= '9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // TcSimple.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EXPONENT"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:424:16: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // TcSimple.g:424:18: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // TcSimple.g:424:42: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0 >= '0' && LA6_0 <= '9')||(LA6_0 >= 'A' && LA6_0 <= 'Z')||LA6_0=='_'||(LA6_0 >= 'a' && LA6_0 <= 'z')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // TcSimple.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:426:7: ( '\"' ( EscapeSequence |~ ( '\\\\' | '\"' | '\\r' | '\\n' ) )* '\"' | '\\'' (~ ( '\\'' | '\\r' | '\\n' ) )* '\\'' )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='\"') ) {
                alt9=1;
            }
            else if ( (LA9_0=='\'') ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;

            }
            switch (alt9) {
                case 1 :
                    // TcSimple.g:426:10: '\"' ( EscapeSequence |~ ( '\\\\' | '\"' | '\\r' | '\\n' ) )* '\"'
                    {
                    match('\"'); 

                    // TcSimple.g:426:14: ( EscapeSequence |~ ( '\\\\' | '\"' | '\\r' | '\\n' ) )*
                    loop7:
                    do {
                        int alt7=3;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0=='\\') ) {
                            alt7=1;
                        }
                        else if ( ((LA7_0 >= '\u0000' && LA7_0 <= '\t')||(LA7_0 >= '\u000B' && LA7_0 <= '\f')||(LA7_0 >= '\u000E' && LA7_0 <= '!')||(LA7_0 >= '#' && LA7_0 <= '[')||(LA7_0 >= ']' && LA7_0 <= '\uFFFF')) ) {
                            alt7=2;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // TcSimple.g:426:16: EscapeSequence
                    	    {
                    	    mEscapeSequence(); 


                    	    }
                    	    break;
                    	case 2 :
                    	    // TcSimple.g:426:33: ~ ( '\\\\' | '\"' | '\\r' | '\\n' )
                    	    {
                    	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);


                    match('\"'); 

                     setText(getText().substring(1, getText().length()-1)); 

                    }
                    break;
                case 2 :
                    // TcSimple.g:427:10: '\\'' (~ ( '\\'' | '\\r' | '\\n' ) )* '\\''
                    {
                    match('\''); 

                    // TcSimple.g:427:15: (~ ( '\\'' | '\\r' | '\\n' ) )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( ((LA8_0 >= '\u0000' && LA8_0 <= '\t')||(LA8_0 >= '\u000B' && LA8_0 <= '\f')||(LA8_0 >= '\u000E' && LA8_0 <= '&')||(LA8_0 >= '(' && LA8_0 <= '\uFFFF')) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // TcSimple.g:
                    	    {
                    	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '\uFFFF') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);


                    match('\''); 

                     setText(getText().substring(1, getText().length()-1)); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "EscapeSequence"
    public final void mEscapeSequence() throws RecognitionException {
        try {
            // TcSimple.g:432:15: ( '\\\\' (| 't' | 'r' | 'n' | '\\\"' ) )
            // TcSimple.g:432:17: '\\\\' (| 't' | 'r' | 'n' | '\\\"' )
            {
            match('\\'); 

            // TcSimple.g:432:22: (| 't' | 'r' | 'n' | '\\\"' )
            int alt10=5;
            switch ( input.LA(1) ) {
            case 't':
                {
                alt10=2;
                }
                break;
            case 'r':
                {
                alt10=3;
                }
                break;
            case 'n':
                {
                alt10=4;
                }
                break;
            case '\"':
                {
                alt10=5;
                }
                break;
            default:
                alt10=1;
            }

            switch (alt10) {
                case 1 :
                    // TcSimple.g:433:14: 
                    {
                    }
                    break;
                case 2 :
                    // TcSimple.g:433:18: 't'
                    {
                    match('t'); 

                    }
                    break;
                case 3 :
                    // TcSimple.g:434:18: 'r'
                    {
                    match('r'); 

                    }
                    break;
                case 4 :
                    // TcSimple.g:435:18: 'n'
                    {
                    match('n'); 

                    }
                    break;
                case 5 :
                    // TcSimple.g:436:18: '\\\"'
                    {
                    match('\"'); 

                    }
                    break;

            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EscapeSequence"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:439:16: ( COMMENTML | COMMENTLINE )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0=='/') ) {
                int LA11_1 = input.LA(2);

                if ( (LA11_1=='*') ) {
                    alt11=1;
                }
                else if ( (LA11_1=='/') ) {
                    alt11=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;

            }
            switch (alt11) {
                case 1 :
                    // TcSimple.g:439:18: COMMENTML
                    {
                    mCOMMENTML(); 


                    }
                    break;
                case 2 :
                    // TcSimple.g:439:30: COMMENTLINE
                    {
                    mCOMMENTLINE(); 


                     _channel = HIDDEN; 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "COMMENTML"
    public final void mCOMMENTML() throws RecognitionException {
        try {
            // TcSimple.g:441:25: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // TcSimple.g:441:27: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 



            // TcSimple.g:441:32: ( options {greedy=false; } : . )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0=='*') ) {
                    int LA12_1 = input.LA(2);

                    if ( (LA12_1=='/') ) {
                        alt12=2;
                    }
                    else if ( ((LA12_1 >= '\u0000' && LA12_1 <= '.')||(LA12_1 >= '0' && LA12_1 <= '\uFFFF')) ) {
                        alt12=1;
                    }


                }
                else if ( ((LA12_0 >= '\u0000' && LA12_0 <= ')')||(LA12_0 >= '+' && LA12_0 <= '\uFFFF')) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // TcSimple.g:441:58: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            match("*/"); 



            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMENTML"

    // $ANTLR start "COMMENTLINE"
    public final void mCOMMENTLINE() throws RecognitionException {
        try {
            // TcSimple.g:442:25: ( '//' (~ ( '\\r' | '\\n' ) )* ( '\\r' )? ( '\\n' )? )
            // TcSimple.g:442:27: '//' (~ ( '\\r' | '\\n' ) )* ( '\\r' )? ( '\\n' )?
            {
            match("//"); 



            // TcSimple.g:442:32: (~ ( '\\r' | '\\n' ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0 >= '\u0000' && LA13_0 <= '\t')||(LA13_0 >= '\u000B' && LA13_0 <= '\f')||(LA13_0 >= '\u000E' && LA13_0 <= '\uFFFF')) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // TcSimple.g:
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);


            // TcSimple.g:442:52: ( '\\r' )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0=='\r') ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // TcSimple.g:442:52: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }


            // TcSimple.g:442:58: ( '\\n' )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='\n') ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // TcSimple.g:442:58: '\\n'
                    {
                    match('\n'); 

                    }
                    break;

            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMENTLINE"

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // TcSimple.g:444:16: ( ( '\\r' | '\\n' | ' ' | '\\f' | '\\t' )+ )
            // TcSimple.g:444:18: ( '\\r' | '\\n' | ' ' | '\\f' | '\\t' )+
            {
            // TcSimple.g:444:18: ( '\\r' | '\\n' | ' ' | '\\f' | '\\t' )+
            int cnt16=0;
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0 >= '\t' && LA16_0 <= '\n')||(LA16_0 >= '\f' && LA16_0 <= '\r')||LA16_0==' ') ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // TcSimple.g:
            	    {
            	    if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||(input.LA(1) >= '\f' && input.LA(1) <= '\r')||input.LA(1)==' ' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt16 >= 1 ) break loop16;
                        EarlyExitException eee =
                            new EarlyExitException(16, input);
                        throw eee;
                }
                cnt16++;
            } while (true);


             _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WHITESPACE"

    public void mTokens() throws RecognitionException {
        // TcSimple.g:1:8: ( ASTERISK | BRACKET_CLOSE | BRACKET_OPEN | COLON | COMMA | COMPARE_BIGGER | COMPARE_BIGGEREQUAL | COMPARE_EQUAL | COMPARE_EQUAL_CSTYLE | COMPARE_LESSEQUAL | COMPARE_NOTEQUAL | COMPARE_NOTEQUAL_CSTYLE | COMPARE_SMALLER | COMPARE_STR_AFTER | COMPARE_STR_AHEAD | COMPARE_STR_ALIKE | COMPARE_STR_BEFORE | COMPARE_STR_BEHIND | COMPARE_STR_EQUAL | COMPARE_STR_NOTAFTER | COMPARE_STR_NOTAHEAD | COMPARE_STR_NOTALIKE | COMPARE_STR_NOTBEFORE | COMPARE_STR_NOTBEHIND | COMPARE_STR_NOTEQUAL | CURLY_CLOSE | CURLY_OPEN | DIV | DOT | DOTS | DOUBLEASTERISK | KEYWORD_AS | KEYWORD_CALC | KEYWORD_CASE | KEYWORD_CELL | KEYWORD_CELLX | KEYWORD_COLLATE | KEYWORD_COUNTERLIST | KEYWORD_DEFAULT | KEYWORD_DOCALL | KEYWORD_ELSE | KEYWORD_ENDCASE | KEYWORD_ENDIF | KEYWORD_EXISTS | KEYWORD_EXTRACT | KEYWORD_FUNC | KEYWORD_FUNCREF | KEYWORD_IF | KEYWORD_INPUT | KEYWORD_INTERPOL | KEYWORD_LINK | KEYWORD_LOOKDOWNX | KEYWORD_LOOKUP | KEYWORD_LOOKUPX | KEYWORD_NODE | KEYWORD_PRODX | KEYWORD_SUMX | KEYWORD_TABCOLS | KEYWORD_TABLE | KEYWORD_TABREF | KEYWORD_TABROWS | KEYWORD_THEN | KEYWORD_TIMES | KEYWORD_TREE | KEYWORD_VECTORX | KEYWORD_WHEN | LOGICAL_AND | LOGICAL_OR | LOGICAL_XOR | MINUS | MOD | PAREN_CLOSE | PAREN_OPEN | PLUS | POWER | QUESTIONMARK | SEMICOLON | SLASH | STRCAT | NUMBER | ID | STRING | COMMENT | WHITESPACE )
        int alt17=84;
        alt17 = dfa17.predict(input);
        switch (alt17) {
            case 1 :
                // TcSimple.g:1:10: ASTERISK
                {
                mASTERISK(); 


                }
                break;
            case 2 :
                // TcSimple.g:1:19: BRACKET_CLOSE
                {
                mBRACKET_CLOSE(); 


                }
                break;
            case 3 :
                // TcSimple.g:1:33: BRACKET_OPEN
                {
                mBRACKET_OPEN(); 


                }
                break;
            case 4 :
                // TcSimple.g:1:46: COLON
                {
                mCOLON(); 


                }
                break;
            case 5 :
                // TcSimple.g:1:52: COMMA
                {
                mCOMMA(); 


                }
                break;
            case 6 :
                // TcSimple.g:1:58: COMPARE_BIGGER
                {
                mCOMPARE_BIGGER(); 


                }
                break;
            case 7 :
                // TcSimple.g:1:73: COMPARE_BIGGEREQUAL
                {
                mCOMPARE_BIGGEREQUAL(); 


                }
                break;
            case 8 :
                // TcSimple.g:1:93: COMPARE_EQUAL
                {
                mCOMPARE_EQUAL(); 


                }
                break;
            case 9 :
                // TcSimple.g:1:107: COMPARE_EQUAL_CSTYLE
                {
                mCOMPARE_EQUAL_CSTYLE(); 


                }
                break;
            case 10 :
                // TcSimple.g:1:128: COMPARE_LESSEQUAL
                {
                mCOMPARE_LESSEQUAL(); 


                }
                break;
            case 11 :
                // TcSimple.g:1:146: COMPARE_NOTEQUAL
                {
                mCOMPARE_NOTEQUAL(); 


                }
                break;
            case 12 :
                // TcSimple.g:1:163: COMPARE_NOTEQUAL_CSTYLE
                {
                mCOMPARE_NOTEQUAL_CSTYLE(); 


                }
                break;
            case 13 :
                // TcSimple.g:1:187: COMPARE_SMALLER
                {
                mCOMPARE_SMALLER(); 


                }
                break;
            case 14 :
                // TcSimple.g:1:203: COMPARE_STR_AFTER
                {
                mCOMPARE_STR_AFTER(); 


                }
                break;
            case 15 :
                // TcSimple.g:1:221: COMPARE_STR_AHEAD
                {
                mCOMPARE_STR_AHEAD(); 


                }
                break;
            case 16 :
                // TcSimple.g:1:239: COMPARE_STR_ALIKE
                {
                mCOMPARE_STR_ALIKE(); 


                }
                break;
            case 17 :
                // TcSimple.g:1:257: COMPARE_STR_BEFORE
                {
                mCOMPARE_STR_BEFORE(); 


                }
                break;
            case 18 :
                // TcSimple.g:1:276: COMPARE_STR_BEHIND
                {
                mCOMPARE_STR_BEHIND(); 


                }
                break;
            case 19 :
                // TcSimple.g:1:295: COMPARE_STR_EQUAL
                {
                mCOMPARE_STR_EQUAL(); 


                }
                break;
            case 20 :
                // TcSimple.g:1:313: COMPARE_STR_NOTAFTER
                {
                mCOMPARE_STR_NOTAFTER(); 


                }
                break;
            case 21 :
                // TcSimple.g:1:334: COMPARE_STR_NOTAHEAD
                {
                mCOMPARE_STR_NOTAHEAD(); 


                }
                break;
            case 22 :
                // TcSimple.g:1:355: COMPARE_STR_NOTALIKE
                {
                mCOMPARE_STR_NOTALIKE(); 


                }
                break;
            case 23 :
                // TcSimple.g:1:376: COMPARE_STR_NOTBEFORE
                {
                mCOMPARE_STR_NOTBEFORE(); 


                }
                break;
            case 24 :
                // TcSimple.g:1:398: COMPARE_STR_NOTBEHIND
                {
                mCOMPARE_STR_NOTBEHIND(); 


                }
                break;
            case 25 :
                // TcSimple.g:1:420: COMPARE_STR_NOTEQUAL
                {
                mCOMPARE_STR_NOTEQUAL(); 


                }
                break;
            case 26 :
                // TcSimple.g:1:441: CURLY_CLOSE
                {
                mCURLY_CLOSE(); 


                }
                break;
            case 27 :
                // TcSimple.g:1:453: CURLY_OPEN
                {
                mCURLY_OPEN(); 


                }
                break;
            case 28 :
                // TcSimple.g:1:464: DIV
                {
                mDIV(); 


                }
                break;
            case 29 :
                // TcSimple.g:1:468: DOT
                {
                mDOT(); 


                }
                break;
            case 30 :
                // TcSimple.g:1:472: DOTS
                {
                mDOTS(); 


                }
                break;
            case 31 :
                // TcSimple.g:1:477: DOUBLEASTERISK
                {
                mDOUBLEASTERISK(); 


                }
                break;
            case 32 :
                // TcSimple.g:1:492: KEYWORD_AS
                {
                mKEYWORD_AS(); 


                }
                break;
            case 33 :
                // TcSimple.g:1:503: KEYWORD_CALC
                {
                mKEYWORD_CALC(); 


                }
                break;
            case 34 :
                // TcSimple.g:1:516: KEYWORD_CASE
                {
                mKEYWORD_CASE(); 


                }
                break;
            case 35 :
                // TcSimple.g:1:529: KEYWORD_CELL
                {
                mKEYWORD_CELL(); 


                }
                break;
            case 36 :
                // TcSimple.g:1:542: KEYWORD_CELLX
                {
                mKEYWORD_CELLX(); 


                }
                break;
            case 37 :
                // TcSimple.g:1:556: KEYWORD_COLLATE
                {
                mKEYWORD_COLLATE(); 


                }
                break;
            case 38 :
                // TcSimple.g:1:572: KEYWORD_COUNTERLIST
                {
                mKEYWORD_COUNTERLIST(); 


                }
                break;
            case 39 :
                // TcSimple.g:1:592: KEYWORD_DEFAULT
                {
                mKEYWORD_DEFAULT(); 


                }
                break;
            case 40 :
                // TcSimple.g:1:608: KEYWORD_DOCALL
                {
                mKEYWORD_DOCALL(); 


                }
                break;
            case 41 :
                // TcSimple.g:1:623: KEYWORD_ELSE
                {
                mKEYWORD_ELSE(); 


                }
                break;
            case 42 :
                // TcSimple.g:1:636: KEYWORD_ENDCASE
                {
                mKEYWORD_ENDCASE(); 


                }
                break;
            case 43 :
                // TcSimple.g:1:652: KEYWORD_ENDIF
                {
                mKEYWORD_ENDIF(); 


                }
                break;
            case 44 :
                // TcSimple.g:1:666: KEYWORD_EXISTS
                {
                mKEYWORD_EXISTS(); 


                }
                break;
            case 45 :
                // TcSimple.g:1:681: KEYWORD_EXTRACT
                {
                mKEYWORD_EXTRACT(); 


                }
                break;
            case 46 :
                // TcSimple.g:1:697: KEYWORD_FUNC
                {
                mKEYWORD_FUNC(); 


                }
                break;
            case 47 :
                // TcSimple.g:1:710: KEYWORD_FUNCREF
                {
                mKEYWORD_FUNCREF(); 


                }
                break;
            case 48 :
                // TcSimple.g:1:726: KEYWORD_IF
                {
                mKEYWORD_IF(); 


                }
                break;
            case 49 :
                // TcSimple.g:1:737: KEYWORD_INPUT
                {
                mKEYWORD_INPUT(); 


                }
                break;
            case 50 :
                // TcSimple.g:1:751: KEYWORD_INTERPOL
                {
                mKEYWORD_INTERPOL(); 


                }
                break;
            case 51 :
                // TcSimple.g:1:768: KEYWORD_LINK
                {
                mKEYWORD_LINK(); 


                }
                break;
            case 52 :
                // TcSimple.g:1:781: KEYWORD_LOOKDOWNX
                {
                mKEYWORD_LOOKDOWNX(); 


                }
                break;
            case 53 :
                // TcSimple.g:1:799: KEYWORD_LOOKUP
                {
                mKEYWORD_LOOKUP(); 


                }
                break;
            case 54 :
                // TcSimple.g:1:814: KEYWORD_LOOKUPX
                {
                mKEYWORD_LOOKUPX(); 


                }
                break;
            case 55 :
                // TcSimple.g:1:830: KEYWORD_NODE
                {
                mKEYWORD_NODE(); 


                }
                break;
            case 56 :
                // TcSimple.g:1:843: KEYWORD_PRODX
                {
                mKEYWORD_PRODX(); 


                }
                break;
            case 57 :
                // TcSimple.g:1:857: KEYWORD_SUMX
                {
                mKEYWORD_SUMX(); 


                }
                break;
            case 58 :
                // TcSimple.g:1:870: KEYWORD_TABCOLS
                {
                mKEYWORD_TABCOLS(); 


                }
                break;
            case 59 :
                // TcSimple.g:1:886: KEYWORD_TABLE
                {
                mKEYWORD_TABLE(); 


                }
                break;
            case 60 :
                // TcSimple.g:1:900: KEYWORD_TABREF
                {
                mKEYWORD_TABREF(); 


                }
                break;
            case 61 :
                // TcSimple.g:1:915: KEYWORD_TABROWS
                {
                mKEYWORD_TABROWS(); 


                }
                break;
            case 62 :
                // TcSimple.g:1:931: KEYWORD_THEN
                {
                mKEYWORD_THEN(); 


                }
                break;
            case 63 :
                // TcSimple.g:1:944: KEYWORD_TIMES
                {
                mKEYWORD_TIMES(); 


                }
                break;
            case 64 :
                // TcSimple.g:1:958: KEYWORD_TREE
                {
                mKEYWORD_TREE(); 


                }
                break;
            case 65 :
                // TcSimple.g:1:971: KEYWORD_VECTORX
                {
                mKEYWORD_VECTORX(); 


                }
                break;
            case 66 :
                // TcSimple.g:1:987: KEYWORD_WHEN
                {
                mKEYWORD_WHEN(); 


                }
                break;
            case 67 :
                // TcSimple.g:1:1000: LOGICAL_AND
                {
                mLOGICAL_AND(); 


                }
                break;
            case 68 :
                // TcSimple.g:1:1012: LOGICAL_OR
                {
                mLOGICAL_OR(); 


                }
                break;
            case 69 :
                // TcSimple.g:1:1023: LOGICAL_XOR
                {
                mLOGICAL_XOR(); 


                }
                break;
            case 70 :
                // TcSimple.g:1:1035: MINUS
                {
                mMINUS(); 


                }
                break;
            case 71 :
                // TcSimple.g:1:1041: MOD
                {
                mMOD(); 


                }
                break;
            case 72 :
                // TcSimple.g:1:1045: PAREN_CLOSE
                {
                mPAREN_CLOSE(); 


                }
                break;
            case 73 :
                // TcSimple.g:1:1057: PAREN_OPEN
                {
                mPAREN_OPEN(); 


                }
                break;
            case 74 :
                // TcSimple.g:1:1068: PLUS
                {
                mPLUS(); 


                }
                break;
            case 75 :
                // TcSimple.g:1:1073: POWER
                {
                mPOWER(); 


                }
                break;
            case 76 :
                // TcSimple.g:1:1079: QUESTIONMARK
                {
                mQUESTIONMARK(); 


                }
                break;
            case 77 :
                // TcSimple.g:1:1092: SEMICOLON
                {
                mSEMICOLON(); 


                }
                break;
            case 78 :
                // TcSimple.g:1:1102: SLASH
                {
                mSLASH(); 


                }
                break;
            case 79 :
                // TcSimple.g:1:1108: STRCAT
                {
                mSTRCAT(); 


                }
                break;
            case 80 :
                // TcSimple.g:1:1115: NUMBER
                {
                mNUMBER(); 


                }
                break;
            case 81 :
                // TcSimple.g:1:1122: ID
                {
                mID(); 


                }
                break;
            case 82 :
                // TcSimple.g:1:1125: STRING
                {
                mSTRING(); 


                }
                break;
            case 83 :
                // TcSimple.g:1:1132: COMMENT
                {
                mCOMMENT(); 


                }
                break;
            case 84 :
                // TcSimple.g:1:1140: WHITESPACE
                {
                mWHITESPACE(); 


                }
                break;

        }

    }


    protected DFA2 dfa2 = new DFA2(this);
    protected DFA17 dfa17 = new DFA17(this);
    static final String DFA2_eotS =
        "\1\uffff\1\2\3\uffff";
    static final String DFA2_eofS =
        "\5\uffff";
    static final String DFA2_minS =
        "\1\60\1\56\3\uffff";
    static final String DFA2_maxS =
        "\1\71\1\145\3\uffff";
    static final String DFA2_acceptS =
        "\2\uffff\1\1\1\2\1\3";
    static final String DFA2_specialS =
        "\5\uffff}>";
    static final String[] DFA2_transitionS = {
            "\12\1",
            "\1\4\1\uffff\12\1\13\uffff\1\3\37\uffff\1\3",
            "",
            "",
            ""
    };

    static final short[] DFA2_eot = DFA.unpackEncodedString(DFA2_eotS);
    static final short[] DFA2_eof = DFA.unpackEncodedString(DFA2_eofS);
    static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars(DFA2_minS);
    static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars(DFA2_maxS);
    static final short[] DFA2_accept = DFA.unpackEncodedString(DFA2_acceptS);
    static final short[] DFA2_special = DFA.unpackEncodedString(DFA2_specialS);
    static final short[][] DFA2_transition;

    static {
        int numStates = DFA2_transitionS.length;
        DFA2_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
        }
    }

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = DFA2_eot;
            this.eof = DFA2_eof;
            this.min = DFA2_min;
            this.max = DFA2_max;
            this.accept = DFA2_accept;
            this.special = DFA2_special;
            this.transition = DFA2_transition;
        }
        public String getDescription() {
            return "414:1: NUMBER : ( NUMBER_INT | NUMBER_INT EXPONENT | NUMBER_INT '.' NUMBER_INT ( EXPONENT )? );";
        }
    }
    static final String DFA17_eotS =
        "\1\uffff\1\62\4\uffff\1\64\1\66\1\71\1\uffff\1\56\2\uffff\1\56\1"+
        "\102\22\56\1\134\1\uffff\1\56\1\uffff\1\56\6\uffff\1\140\15\uffff"+
        "\1\56\1\146\1\150\1\uffff\3\56\2\uffff\1\154\11\56\1\171\15\56\2"+
        "\uffff\2\56\2\uffff\1\u008a\1\u008d\6\uffff\1\56\1\u008f\1\56\1"+
        "\uffff\14\56\1\uffff\15\56\1\u00ac\1\u00ad\5\uffff\1\u00ae\1\uffff"+
        "\1\56\1\u00b0\1\u00b1\1\u00b3\3\56\1\u00b7\4\56\1\u00bc\3\56\1\u00c0"+
        "\1\56\1\u00c3\4\56\1\u00c9\1\56\1\u00cb\1\56\1\u00cd\3\uffff\1\56"+
        "\2\uffff\1\u00cf\1\uffff\3\56\1\uffff\1\56\1\u00d4\2\56\1\uffff"+
        "\1\56\1\u00d8\1\56\1\uffff\2\56\1\uffff\1\u00dc\3\56\1\u00e0\1\uffff"+
        "\1\u00e1\1\uffff\1\56\1\uffff\1\56\1\uffff\2\56\1\u00e6\1\56\1\uffff"+
        "\1\u00e8\2\56\1\uffff\2\56\1\u00ee\1\uffff\1\56\1\u00f0\1\56\2\uffff"+
        "\1\56\1\u00f3\1\u00f4\1\56\1\uffff\1\u00f6\1\uffff\1\u00f7\1\u00f8"+
        "\2\56\1\u00fb\1\uffff\1\u00fc\1\uffff\1\u00fd\1\u00fe\2\uffff\1"+
        "\56\3\uffff\1\u0100\1\56\4\uffff\1\56\1\uffff\1\u0103\1\56\1\uffff"+
        "\1\u0105\1\uffff";
    static final String DFA17_eofS =
        "\u0106\uffff";
    static final String DFA17_minS =
        "\1\11\1\52\4\uffff\3\75\1\uffff\1\74\2\uffff\1\105\1\56\1\123\1"+
        "\101\1\145\1\157\1\114\1\170\1\125\1\165\1\106\1\156\1\111\1\157"+
        "\1\117\1\162\1\141\1\101\1\145\1\110\1\46\1\uffff\1\117\1\uffff"+
        "\1\117\6\uffff\1\52\15\uffff\1\74\2\75\1\uffff\1\155\1\126\1\106"+
        "\2\uffff\1\60\1\114\2\154\1\143\1\123\1\104\1\151\1\116\1\156\1"+
        "\60\1\120\1\164\1\116\1\157\1\104\1\157\1\142\1\102\1\105\1\115"+
        "\1\105\1\143\1\105\2\uffff\1\122\1\104\2\uffff\2\75\6\uffff\1\170"+
        "\1\60\1\101\1\uffff\1\103\1\105\2\154\1\156\1\141\1\105\1\103\1"+
        "\163\1\162\1\103\1\143\1\uffff\1\125\1\145\1\113\1\153\1\105\1\144"+
        "\1\143\1\114\1\116\2\105\1\164\1\116\2\60\5\uffff\1\60\1\uffff\1"+
        "\125\3\60\1\141\1\164\1\154\1\60\1\101\1\106\1\164\1\141\1\60\1"+
        "\162\1\124\1\162\1\60\1\144\1\60\1\170\1\157\1\145\1\105\1\60\1"+
        "\123\1\60\1\157\1\60\3\uffff\1\114\2\uffff\1\60\1\uffff\1\164\1"+
        "\145\1\154\1\uffff\1\123\1\60\1\163\1\143\1\uffff\1\145\1\60\1\160"+
        "\1\uffff\1\157\1\160\1\uffff\1\60\1\154\1\146\1\167\1\60\1\uffff"+
        "\1\60\1\uffff\1\162\1\uffff\1\124\1\uffff\1\145\1\162\1\60\1\105"+
        "\1\uffff\1\60\1\164\1\146\1\uffff\1\157\1\167\1\60\1\uffff\1\163"+
        "\1\60\1\163\2\uffff\1\170\2\60\1\154\1\uffff\1\60\1\uffff\2\60\1"+
        "\154\1\156\1\60\1\uffff\1\60\1\uffff\2\60\2\uffff\1\151\3\uffff"+
        "\1\60\1\170\4\uffff\1\163\1\uffff\1\60\1\164\1\uffff\1\60\1\uffff";
    static final String DFA17_maxS =
        "\1\175\1\52\4\uffff\2\75\1\76\1\uffff\1\165\2\uffff\1\111\1\56\1"+
        "\123\1\101\2\157\1\116\1\170\1\125\1\165\1\116\1\156\1\111\1\157"+
        "\1\117\1\162\1\141\1\122\1\145\1\110\1\46\1\uffff\1\117\1\uffff"+
        "\1\117\6\uffff\1\57\15\uffff\2\76\1\75\1\uffff\1\155\1\126\1\106"+
        "\2\uffff\1\172\1\123\1\154\1\165\1\143\1\123\1\104\1\164\1\116\1"+
        "\156\1\172\1\120\1\164\1\116\1\157\1\104\1\157\1\142\1\102\1\105"+
        "\1\115\1\105\1\143\1\105\2\uffff\1\122\1\104\2\uffff\1\75\1\76\6"+
        "\uffff\1\170\1\172\1\101\1\uffff\1\103\1\105\2\154\1\156\1\141\1"+
        "\105\1\111\1\163\1\162\1\103\1\143\1\uffff\1\125\1\145\1\113\1\153"+
        "\1\105\1\144\1\162\1\114\1\116\2\105\1\164\1\116\2\172\5\uffff\1"+
        "\172\1\uffff\1\125\3\172\1\141\1\164\1\154\1\172\1\101\1\106\1\164"+
        "\1\141\1\172\1\162\1\124\1\162\1\172\1\165\1\172\1\170\2\157\1\105"+
        "\1\172\1\123\1\172\1\157\1\172\3\uffff\1\114\2\uffff\1\172\1\uffff"+
        "\1\164\1\145\1\154\1\uffff\1\123\1\172\1\163\1\143\1\uffff\1\145"+
        "\1\172\1\160\1\uffff\1\157\1\160\1\uffff\1\172\1\154\1\146\1\167"+
        "\1\172\1\uffff\1\172\1\uffff\1\162\1\uffff\1\124\1\uffff\1\145\1"+
        "\162\1\172\1\105\1\uffff\1\172\1\164\1\146\1\uffff\1\157\1\167\1"+
        "\172\1\uffff\1\163\1\172\1\163\2\uffff\1\170\2\172\1\154\1\uffff"+
        "\1\172\1\uffff\2\172\1\154\1\156\1\172\1\uffff\1\172\1\uffff\2\172"+
        "\2\uffff\1\151\3\uffff\1\172\1\170\4\uffff\1\163\1\uffff\1\172\1"+
        "\164\1\uffff\1\172\1\uffff";
    static final String DFA17_acceptS =
        "\2\uffff\1\2\1\3\1\4\1\5\3\uffff\1\14\1\uffff\1\32\1\33\25\uffff"+
        "\1\104\1\uffff\1\106\1\uffff\1\110\1\111\1\112\1\113\1\114\1\115"+
        "\1\uffff\1\120\1\121\1\122\1\124\1\37\1\1\1\7\1\6\1\11\1\10\1\12"+
        "\1\13\1\15\3\uffff\1\23\3\uffff\1\36\1\35\30\uffff\1\103\1\117\2"+
        "\uffff\1\123\1\116\2\uffff\1\20\1\30\1\31\1\21\1\27\1\22\3\uffff"+
        "\1\40\14\uffff\1\60\17\uffff\1\25\1\16\1\24\1\26\1\17\1\uffff\1"+
        "\34\34\uffff\1\105\1\107\1\71\1\uffff\1\41\1\42\1\uffff\1\43\3\uffff"+
        "\1\51\4\uffff\1\56\3\uffff\1\63\2\uffff\1\67\5\uffff\1\76\1\uffff"+
        "\1\100\1\uffff\1\102\1\uffff\1\44\4\uffff\1\53\3\uffff\1\61\3\uffff"+
        "\1\70\3\uffff\1\73\1\77\4\uffff\1\50\1\uffff\1\54\5\uffff\1\65\1"+
        "\uffff\1\74\2\uffff\1\47\1\45\1\uffff\1\52\1\55\1\57\2\uffff\1\66"+
        "\1\72\1\75\1\101\1\uffff\1\62\2\uffff\1\64\1\uffff\1\46";
    static final String DFA17_specialS =
        "\u0106\uffff}>";
    static final String[] DFA17_transitionS = {
            "\2\60\1\uffff\2\60\22\uffff\1\60\1\11\1\57\3\uffff\1\41\1\57"+
            "\1\47\1\46\1\1\1\50\1\5\1\44\1\16\1\54\12\55\1\4\1\53\1\10\1"+
            "\7\1\6\1\52\1\uffff\1\17\1\56\1\20\1\15\1\23\1\25\2\56\1\27"+
            "\2\56\1\31\1\45\1\33\5\56\1\36\2\56\1\40\1\43\2\56\1\3\1\uffff"+
            "\1\2\1\51\1\56\1\uffff\2\56\1\21\1\22\1\24\1\26\2\56\1\30\2"+
            "\56\1\32\3\56\1\34\2\56\1\12\1\35\1\56\1\37\4\56\1\14\1\42\1"+
            "\13",
            "\1\61",
            "",
            "",
            "",
            "",
            "\1\63",
            "\1\65",
            "\1\67\1\70",
            "",
            "\1\73\1\75\1\74\52\uffff\1\72\13\uffff\1\76",
            "",
            "",
            "\1\100\3\uffff\1\77",
            "\1\101",
            "\1\103",
            "\1\104",
            "\1\105\11\uffff\1\106",
            "\1\107",
            "\1\110\1\uffff\1\111",
            "\1\112",
            "\1\113",
            "\1\114",
            "\1\115\7\uffff\1\116",
            "\1\117",
            "\1\120",
            "\1\121",
            "\1\122",
            "\1\123",
            "\1\124",
            "\1\125\6\uffff\1\126\1\127\10\uffff\1\130",
            "\1\131",
            "\1\132",
            "\1\133",
            "",
            "\1\135",
            "",
            "\1\136",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\137\4\uffff\1\137",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\142\1\143\1\141",
            "\1\144\1\145",
            "\1\147",
            "",
            "\1\151",
            "\1\152",
            "\1\153",
            "",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\155\6\uffff\1\156",
            "\1\157",
            "\1\160\10\uffff\1\161",
            "\1\162",
            "\1\163",
            "\1\164",
            "\1\165\12\uffff\1\166",
            "\1\167",
            "\1\170",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\172",
            "\1\173",
            "\1\174",
            "\1\175",
            "\1\176",
            "\1\177",
            "\1\u0080",
            "\1\u0081",
            "\1\u0082",
            "\1\u0083",
            "\1\u0084",
            "\1\u0085",
            "\1\u0086",
            "",
            "",
            "\1\u0087",
            "\1\u0088",
            "",
            "",
            "\1\u0089",
            "\1\u008b\1\u008c",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u008e",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u0090",
            "",
            "\1\u0091",
            "\1\u0092",
            "\1\u0093",
            "\1\u0094",
            "\1\u0095",
            "\1\u0096",
            "\1\u0097",
            "\1\u0098\5\uffff\1\u0099",
            "\1\u009a",
            "\1\u009b",
            "\1\u009c",
            "\1\u009d",
            "",
            "\1\u009e",
            "\1\u009f",
            "\1\u00a0",
            "\1\u00a1",
            "\1\u00a2",
            "\1\u00a3",
            "\1\u00a4\16\uffff\1\u00a5",
            "\1\u00a6",
            "\1\u00a7",
            "\1\u00a8",
            "\1\u00a9",
            "\1\u00aa",
            "\1\u00ab",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "",
            "",
            "",
            "",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "",
            "\1\u00af",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\27\56\1\u00b2\2\56",
            "\1\u00b4",
            "\1\u00b5",
            "\1\u00b6",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00b8",
            "\1\u00b9",
            "\1\u00ba",
            "\1\u00bb",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00bd",
            "\1\u00be",
            "\1\u00bf",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00c1\20\uffff\1\u00c2",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00c4",
            "\1\u00c5",
            "\1\u00c6\11\uffff\1\u00c7",
            "\1\u00c8",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00ca",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00cc",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "",
            "",
            "",
            "\1\u00ce",
            "",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "",
            "\1\u00d0",
            "\1\u00d1",
            "\1\u00d2",
            "",
            "\1\u00d3",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00d5",
            "\1\u00d6",
            "",
            "\1\u00d7",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00d9",
            "",
            "\1\u00da",
            "\1\u00db",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00dd",
            "\1\u00de",
            "\1\u00df",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "",
            "\1\u00e2",
            "",
            "\1\u00e3",
            "",
            "\1\u00e4",
            "\1\u00e5",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00e7",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00e9",
            "\1\u00ea",
            "",
            "\1\u00eb",
            "\1\u00ec",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\27\56\1\u00ed\2\56",
            "",
            "\1\u00ef",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00f1",
            "",
            "",
            "\1\u00f2",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00f5",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00f9",
            "\1\u00fa",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "",
            "",
            "\1\u00ff",
            "",
            "",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u0101",
            "",
            "",
            "",
            "",
            "\1\u0102",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u0104",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            ""
    };

    static final short[] DFA17_eot = DFA.unpackEncodedString(DFA17_eotS);
    static final short[] DFA17_eof = DFA.unpackEncodedString(DFA17_eofS);
    static final char[] DFA17_min = DFA.unpackEncodedStringToUnsignedChars(DFA17_minS);
    static final char[] DFA17_max = DFA.unpackEncodedStringToUnsignedChars(DFA17_maxS);
    static final short[] DFA17_accept = DFA.unpackEncodedString(DFA17_acceptS);
    static final short[] DFA17_special = DFA.unpackEncodedString(DFA17_specialS);
    static final short[][] DFA17_transition;

    static {
        int numStates = DFA17_transitionS.length;
        DFA17_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA17_transition[i] = DFA.unpackEncodedString(DFA17_transitionS[i]);
        }
    }

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = DFA17_eot;
            this.eof = DFA17_eof;
            this.min = DFA17_min;
            this.max = DFA17_max;
            this.accept = DFA17_accept;
            this.special = DFA17_special;
            this.transition = DFA17_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( ASTERISK | BRACKET_CLOSE | BRACKET_OPEN | COLON | COMMA | COMPARE_BIGGER | COMPARE_BIGGEREQUAL | COMPARE_EQUAL | COMPARE_EQUAL_CSTYLE | COMPARE_LESSEQUAL | COMPARE_NOTEQUAL | COMPARE_NOTEQUAL_CSTYLE | COMPARE_SMALLER | COMPARE_STR_AFTER | COMPARE_STR_AHEAD | COMPARE_STR_ALIKE | COMPARE_STR_BEFORE | COMPARE_STR_BEHIND | COMPARE_STR_EQUAL | COMPARE_STR_NOTAFTER | COMPARE_STR_NOTAHEAD | COMPARE_STR_NOTALIKE | COMPARE_STR_NOTBEFORE | COMPARE_STR_NOTBEHIND | COMPARE_STR_NOTEQUAL | CURLY_CLOSE | CURLY_OPEN | DIV | DOT | DOTS | DOUBLEASTERISK | KEYWORD_AS | KEYWORD_CALC | KEYWORD_CASE | KEYWORD_CELL | KEYWORD_CELLX | KEYWORD_COLLATE | KEYWORD_COUNTERLIST | KEYWORD_DEFAULT | KEYWORD_DOCALL | KEYWORD_ELSE | KEYWORD_ENDCASE | KEYWORD_ENDIF | KEYWORD_EXISTS | KEYWORD_EXTRACT | KEYWORD_FUNC | KEYWORD_FUNCREF | KEYWORD_IF | KEYWORD_INPUT | KEYWORD_INTERPOL | KEYWORD_LINK | KEYWORD_LOOKDOWNX | KEYWORD_LOOKUP | KEYWORD_LOOKUPX | KEYWORD_NODE | KEYWORD_PRODX | KEYWORD_SUMX | KEYWORD_TABCOLS | KEYWORD_TABLE | KEYWORD_TABREF | KEYWORD_TABROWS | KEYWORD_THEN | KEYWORD_TIMES | KEYWORD_TREE | KEYWORD_VECTORX | KEYWORD_WHEN | LOGICAL_AND | LOGICAL_OR | LOGICAL_XOR | MINUS | MOD | PAREN_CLOSE | PAREN_OPEN | PLUS | POWER | QUESTIONMARK | SEMICOLON | SLASH | STRCAT | NUMBER | ID | STRING | COMMENT | WHITESPACE );";
        }
    }
 

}