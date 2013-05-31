grammar TcSimple;
options {
    output=AST;    
    ASTLabelType=TcAst;
}                                

tokens {
    TT_COMPUNIT;
    TT_RESULTDEF;
    TT_ARGDEF;
    TT_TABLELINE;
    TT_NODEPATH;
    TT_IDLIST;
    TT_USEID;
    TT_INPUTCALCCALLSIMPLE;
    TT_INPUTORTABACCESSWITHINDEX;
    TT_DYNTABLE;
    TT_FUNORCALCCALL;
    TT_CASECONDITION;
    TT_CASECOMPARISON;
    TT_CASERANGE;
    TT_COLNAMESTATIC;
    TT_COLNAMEFORMULA;
    TT_PARAMETERS;
    TT_INDEX;
    
    TT_FUNCALL;
    TT_CALCCALL;
    TT_TABLEACCESS;
    TT_INPUTACCESS;     
    TT_INPUTACCESSSIMPLE;
    TT_INPUTCALCCALL;
    TT_INPUTACCESSRAWSELF;
    TT_BUILTIN;
    TT_USEVAR_PARAMETER;
    TT_USEVAR_TIMESINDEX;
    TT_USEVAR_LOCAL;
    TT_TABREF;
    
    TT_INCLUSIONFORMULA;
    TT_TIMESINFO;
    
    LOGICAL_OR =  '||';
    LOGICAL_AND= '&&';
    LOGICAL_XOR= 'XOR';
    
    PLUS= '+';
    MINUS= '-';
    ASTERISK= '*';
    DOUBLEASTERISK= '**';
    SLASH= '/';
    DIV= 'DIV';
    MOD= 'MOD';
    POWER= '^';
    
    STRCAT= '&';
    
    
    DOTS= '..';
    DOT= '.';
    COLON= ':';
    COMMA= ',';
    SEMICOLON= ';';
    
    PAREN_OPEN= '(';
    PAREN_CLOSE= ')';
    BRACKET_OPEN= '[';
    BRACKET_CLOSE= ']';
    CURLY_OPEN= '{';
    CURLY_CLOSE= '}';
    
    QUESTIONMARK='?';
    
    
    COMPARE_STR_EQUAL= 's=';
    COMPARE_STR_NOTEQUAL= 's<>';
    COMPARE_STR_ALIKE= 'si=';
    COMPARE_STR_NOTALIKE= 'si<>';
    COMPARE_STR_BEFORE= 's<';
    COMPARE_STR_NOTBEFORE= 's>=';
    COMPARE_STR_AHEAD= 'si<';
    COMPARE_STR_NOTAHEAD= 'si>=';
    COMPARE_STR_BEHIND= 's>';
    COMPARE_STR_NOTBEHIND= 's<=';
    COMPARE_STR_AFTER= 'si>';
    COMPARE_STR_NOTAFTER= 'si<=';
    
    COMPARE_EQUAL = '=';
    COMPARE_EQUAL_CSTYLE = '==';
    COMPARE_SMALLER= '<';
    COMPARE_BIGGER= '>';
    COMPARE_LESSEQUAL= '<=';
    COMPARE_BIGGEREQUAL= '>=';
    COMPARE_NOTEQUAL= '<>';
    COMPARE_NOTEQUAL_CSTYLE = '!=';
    
    KEYWORD_AS     = 'AS';
    KEYWORD_TABLE  = 'TABLE';
    KEYWORD_TABREF = 'tabref';
    KEYWORD_TIMES  = 'TIMES';
    KEYWORD_TREE   = 'TREE';
    KEYWORD_CALC   = 'CALC';
    KEYWORD_INPUT  = 'INPUT';
    KEYWORD_NODE   = 'NODE';
    KEYWORD_LINK   = 'LINK';
    KEYWORD_FUNC   = 'FUNC';
    KEYWORD_IF     = 'IF';
    KEYWORD_THEN   = 'THEN'; 
    KEYWORD_ELSE   = 'ELSE';
    KEYWORD_ENDIF  = 'ENDIF';
    KEYWORD_CASE   = 'CASE';
    KEYWORD_WHEN   = 'WHEN';
    KEYWORD_DEFAULT= 'DEFAULT';
    KEYWORD_ENDCASE= 'ENDCASE';
    KEYWORD_SUMX   = 'sumx';
    KEYWORD_PRODX  = 'prodx';
    KEYWORD_VECTORX= 'vectorx';
    KEYWORD_EXTRACT= 'extract';
    KEYWORD_COLLATE= 'collate';    
    KEYWORD_CELL   = 'cell';
    KEYWORD_CELLX  = 'cellx';
    KEYWORD_EXISTS = 'exists';
    KEYWORD_INTERPOL = 'interpol';
    KEYWORD_TABCOLS  = 'tabcols';
    KEYWORD_TABROWS  = 'tabrows';
    KEYWORD_LOOKUP    = 'lookup';
    KEYWORD_LOOKUPX   = 'lookupx';
    KEYWORD_LOOKDOWNX = 'lookdownx';
    KEYWORD_FUNCREF   = 'funcref';
    KEYWORD_DOCALL    = 'docall';
    KEYWORD_COUNTERLIST = 'counterlist';
    TT_RANGE;
}


@parser::header       {
	package treecalc.comp.parser;
	import treecalc.comp.TcAst; 
}
@lexer::header        {
	package treecalc.comp.parser; 
	import treecalc.comp.TcAst; 
}

//@members {
//    Scope globalscope;
//    Scope currentscope;
//    public TcSimpleParser(TokenStream input, Scope globalscope) {
//        this(input);
//        this.globalscope = globalscope;
//        currentscope = globalscope;
//    }
//}

compilationunit: def+ -> ^(TT_COMPUNIT def*) ;
def:
   KEYWORD_TREE nodepath CURLY_OPEN nodeinfo* CURLY_CLOSE               -> ^(KEYWORD_TREE nodepath nodeinfo*)
 | KEYWORD_CALC nodepath CURLY_OPEN resultdef* CURLY_CLOSE              -> ^(KEYWORD_CALC  nodepath resultdef*)
 | KEYWORD_INPUT id ((CURLY_OPEN resultdef* CURLY_CLOSE) | SEMICOLON)   -> ^(KEYWORD_INPUT id resultdef*)
 | KEYWORD_FUNC resultdef                                               -> ^(KEYWORD_FUNC resultdef)
 | KEYWORD_TABLE id PAREN_OPEN colnames PAREN_CLOSE  CURLY_OPEN tableline* CURLY_CLOSE
                                                                        -> ^(KEYWORD_TABLE id colnames tableline*)
 ;                                       
 
nodeinfo:
   KEYWORD_NODE nodename (KEYWORD_AS id)? nodeinclusion? nodetimes? (SEMICOLON | (CURLY_OPEN nodeinfo* CURLY_CLOSE))
     -> ^(KEYWORD_NODE nodename ^(KEYWORD_AS id)? nodeinclusion? nodetimes? nodeinfo*)
 | KEYWORD_LINK linkpath SEMICOLON  -> ^(KEYWORD_LINK linkpath)
 ;                                                                         

nodeinclusion:
   kw=KEYWORD_IF formula -> ^(TT_INCLUSIONFORMULA[$kw] formula)
;                                                     

nodetimes:
   kw=KEYWORD_TIMES formula KEYWORD_AS id  -> ^(TT_TIMESINFO[$kw] formula id)
;

resultdef:                                                                 
    id arguments?  COMPARE_EQUAL formula SEMICOLON   -> ^(TT_RESULTDEF id arguments? formula) 
 ;                                                                                  
 
arguments        : PAREN_OPEN id (COMMA id)* PAREN_CLOSE -> ^(TT_ARGDEF id*) ;
                                                                                  
id: 
   ID              -> ID
 | kw=keywordAsId  -> ID[((CommonTree)kw.tree).getText()]
 ;
  
keywordAsId:
   KEYWORD_AS
 | KEYWORD_TABLE  
 | KEYWORD_TREE   
 | KEYWORD_CALC   
 | KEYWORD_INPUT  
 | KEYWORD_FUNC   
 | KEYWORD_NODE
 | KEYWORD_IF     
 | KEYWORD_THEN   
 | KEYWORD_ELSE   
 | KEYWORD_ENDIF  
 | KEYWORD_CASE   
 | KEYWORD_WHEN   
 | KEYWORD_DEFAULT
 | KEYWORD_ENDCASE
 | KEYWORD_COLLATE 
 | KEYWORD_EXTRACT
 | KEYWORD_SUMX   
 | KEYWORD_PRODX
 | KEYWORD_VECTORX 
 | KEYWORD_CELL
 | KEYWORD_CELLX
 | KEYWORD_EXISTS
 | KEYWORD_INTERPOL
 | KEYWORD_TABCOLS
 | KEYWORD_TABROWS
 | KEYWORD_LOOKUP
 | KEYWORD_LOOKUPX
 | KEYWORD_LOOKDOWNX         
 | KEYWORD_FUNCREF
 | KEYWORD_DOCALL
 | KEYWORD_COUNTERLIST
;
                          
constantorid:
  constant
| id
;
                                                          
tableline:
   tablecell (COMMA tablecell)* SEMICOLON        ->  ^(TT_TABLELINE tablecell*)
 ;
                                  
tablecell: 
   constant|id
 ;
                                  
nodename: 
   constant|id
 ;         
nodepath: 
   id (DOT id)*    -> ^(TT_NODEPATH id*)
 ;     
 
linkpath: 
   id (DOT linkpart)*    -> ^(TT_NODEPATH id linkpart*)
 ;                                              
 
linkpart:
   id                                           
 | STRING         
 | ASTERISK       
 | DOUBLEASTERISK 
 ;
  
colnames:
   colname (COMMA colname)*                          -> ^(TT_IDLIST colname*)
 ;                                                                           

colname:
   id 
 | NUMBER
 | STRING
 ;
 
                                            
vpmsformula: formula EOF;

formula:  formula2 (QUESTIONMARK^ formula2 COLON! formula2)*;
formula2: formula3 (LOGICAL_OR^ formula3)*;
formula3: formula4 (LOGICAL_AND^ formula4)*;
formula4: formula5 (LOGICAL_XOR^ formula5)*;
formula5: formula6 (comparisonoperator^ formula6)*;
formula6: formula7 (STRCAT^ formula7)*;
formula7: formula8 ((PLUS|MINUS)^ formula8)*;
formula8: formula9 ((ASTERISK|SLASH|DIV|MOD)^ formula9)*;
formula9: formula10 (POWER^ formula9)?;
formula10: (PLUS^|MINUS^)* expression;

expression:
  PAREN_OPEN! formula PAREN_CLOSE!
| KEYWORD_SUMX  PAREN_OPEN id COMMA formula COMMA formula COMMA formula PAREN_CLOSE
          -> ^(KEYWORD_SUMX id formula*)
| KEYWORD_PRODX PAREN_OPEN id COMMA formula COMMA formula COMMA formula PAREN_CLOSE
          -> ^(KEYWORD_PRODX id formula*)
| KEYWORD_VECTORX PAREN_OPEN id COMMA formula COMMA formula COMMA formula PAREN_CLOSE
          -> ^(KEYWORD_VECTORX id formula*)
| KEYWORD_COLLATE PAREN_OPEN id (PAREN_OPEN formula (COMMA formula)* PAREN_CLOSE)? PAREN_CLOSE
          -> ^(KEYWORD_COLLATE id formula*)
| KEYWORD_EXTRACT PAREN_OPEN id (PAREN_OPEN formula (COMMA formula)* PAREN_CLOSE)? COMMA formula PAREN_CLOSE
          -> ^(KEYWORD_EXTRACT id formula*)
| KEYWORD_CELL PAREN_OPEN tableref COMMA range COMMA range PAREN_CLOSE
          -> ^(KEYWORD_CELL tableref range*)
| KEYWORD_CELLX PAREN_OPEN tableref COMMA range COMMA range PAREN_CLOSE
          -> ^(KEYWORD_CELLX tableref range*)
| KEYWORD_LOOKUP PAREN_OPEN tableref COMMA formula PAREN_CLOSE
          -> ^(KEYWORD_LOOKUP tableref formula*)
| KEYWORD_LOOKUPX PAREN_OPEN tableref COMMA formula (COMMA formula)* PAREN_CLOSE
          -> ^(KEYWORD_LOOKUPX tableref formula*)
| KEYWORD_LOOKDOWNX PAREN_OPEN tableref COMMA formula (COMMA formula)* PAREN_CLOSE
          -> ^(KEYWORD_LOOKDOWNX tableref formula*)
| KEYWORD_EXISTS PAREN_OPEN tableref COMMA formula  (COMMA formula)* PAREN_CLOSE
          -> ^(KEYWORD_EXISTS tableref formula*)
| KEYWORD_INTERPOL PAREN_OPEN tableref COMMA formula PAREN_CLOSE
          -> ^(KEYWORD_INTERPOL tableref formula)
| KEYWORD_TABCOLS PAREN_OPEN tableref PAREN_CLOSE
          -> ^(KEYWORD_TABCOLS tableref)
| KEYWORD_TABROWS PAREN_OPEN tableref PAREN_CLOSE
          -> ^(KEYWORD_TABROWS tableref)
| KEYWORD_FUNCREF PAREN_OPEN formula PAREN_CLOSE
          -> ^(KEYWORD_FUNCREF formula)
| KEYWORD_DOCALL PAREN_OPEN formula (COMMA formula)* PAREN_CLOSE
          -> ^(KEYWORD_DOCALL formula*)
| KEYWORD_COUNTERLIST PAREN_OPEN id (COMMA id)* PAREN_CLOSE
          -> ^(KEYWORD_COUNTERLIST id*)
| ID              -> ^(TT_USEID ID)                  //attribute/property/function/parameter
| ID  DOT  id     -> ^(TT_INPUTCALCCALLSIMPLE ID id)       //attribute.prop
| ID  index columnaccess? -> ^(TT_INPUTORTABACCESSWITHINDEX ID index columnaccess?)
                                                    //attribute[index], table[index]
                                                    //attribute[index].id, table[index].colname
                                                    //table[index](colnameformula)
| dyntable (index columnaccess?)? -> ^(TT_DYNTABLE dyntable index? columnaccess?)
                                                    //same but with table(...); or just table(...) for exists(table(...), ...)
| ID  parameterListe              -> ^(TT_FUNORCALCCALL ID parameterListe)      //property(params), function(params)
| ifstmt
| casestmt
| constant                     
;                       
                          
tableref: 
  id
| dyntable
;
                                                                
range: 
  formula (DOTS formula)?         -> ^(TT_RANGE formula*)
;

ifstmt:
  KEYWORD_IF formula KEYWORD_THEN formula KEYWORD_ELSE formula KEYWORD_ENDIF
                                  -> ^(KEYWORD_IF formula*)
;
             
casestmt:            
  KEYWORD_CASE formula casewhen* casedefault? KEYWORD_ENDCASE
                                  -> ^(KEYWORD_CASE formula casewhen* casedefault?)
;
                                                                                   
casewhen:
  KEYWORD_WHEN casecompares COLON formula   -> ^(KEYWORD_WHEN casecompares formula)
;
casecompares:      
  casecompare (COMMA casecompare)*   -> ^(TT_CASECONDITION casecompare*)
;
  
casecompare:
  caseconstant                               -> ^(TT_CASECOMPARISON caseconstant)
| caseconstantnumber DOTS caseconstantnumber -> ^(TT_CASERANGE      caseconstantnumber*)
| STRING DOTS STRING                         -> ^(TT_CASERANGE      STRING*)
;                                                                                 
  
casedefault:
  KEYWORD_DEFAULT COLON formula      -> ^(KEYWORD_DEFAULT formula)
;
  
caseconstant:
  STRING
| caseconstantnumber
;
caseconstantnumber:
  NUMBER
| MINUS n=NUMBER     -> NUMBER["-" + $n.getText()] 
;

dyntable: KEYWORD_TABREF  PAREN_OPEN  formula  PAREN_CLOSE -> formula
;

columnaccess: 
  DOT id                          -> ^(TT_COLNAMESTATIC  id)
| PAREN_OPEN formula PAREN_CLOSE  -> ^(TT_COLNAMEFORMULA formula)
;

comparisonoperator:
  COMPARE_EQUAL
| COMPARE_EQUAL_CSTYLE
| COMPARE_SMALLER
| COMPARE_BIGGER
| COMPARE_LESSEQUAL
| COMPARE_BIGGEREQUAL
| COMPARE_NOTEQUAL
| COMPARE_NOTEQUAL_CSTYLE
| COMPARE_STR_EQUAL
| COMPARE_STR_NOTEQUAL
| COMPARE_STR_ALIKE
| COMPARE_STR_NOTALIKE
| COMPARE_STR_BEFORE
| COMPARE_STR_NOTBEFORE
| COMPARE_STR_AHEAD
| COMPARE_STR_NOTAHEAD
| COMPARE_STR_BEHIND
| COMPARE_STR_NOTBEHIND
| COMPARE_STR_AFTER
| COMPARE_STR_NOTAFTER
;   


parameterListe: 
  PAREN_OPEN (formula  (COMMA formula)*)? PAREN_CLOSE -> ^(TT_PARAMETERS formula*)
  ;                                                  
                     
index: 
  BRACKET_OPEN formula (COMMA formula)* BRACKET_CLOSE -> ^(TT_INDEX formula*)
  ;
  
constant: 
  STRING         
| NUMBER
;

NUMBER :                       
  NUMBER_INT
| NUMBER_INT EXPONENT
//| '.' NUMBER_INT EXPONENT?
| NUMBER_INT '.' NUMBER_INT EXPONENT?
//| NUMBER_INT '.' EXPONENT
;
fragment NUMBER_INT: '0'..'9'+;
fragment EXPONENT: ('e'|'E') '-'? '0'..'9'+;

ID             : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;

STRING:  '"' ( EscapeSequence | ~( '\\' | '"' | '\r' | '\n' ) )*  '"'   { setText(getText().substring(1, getText().length()-1)); }
   |     '\'' (~('\''|'\r'|'\n'))* '\''                                 { setText(getText().substring(1, getText().length()-1)); }
;

fragment
EscapeSequence: '\\' (
             |   't' 
             |   'r' 
             |   'n' 
             |   '\"' 
             )          
;     

COMMENT        : COMMENTML | COMMENTLINE                               { $channel = HIDDEN; };

fragment COMMENTML      : '/*' (options {greedy=false;} :.)* '*/';
fragment COMMENTLINE    : '//' ( ~('\r' | '\n') )* '\r'? '\n'?   ;

WHITESPACE     : ('\r'|'\n'|' '|'\f'|'\t')+                            { $channel = HIDDEN; };
