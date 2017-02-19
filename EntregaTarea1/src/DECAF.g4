grammar DECAF;

// KEYWORDS
CLASS  :                'class';
STRUCT :                'struct';
TRUE   :                'true';
FALSE  :                'false';
INT    :                'int';
IF     :                'if';
ELSE   :                'else';
WHILE  :                'while';
RETURN :                'return';
CHAR   :                'char';
BOOLEAN:                'boolean';
VOID   :                'void';
SCAN   :                'scan';
PRINT  :                'print';
PROGRAM:                'Program';

// TOKENS
fragment LETTER:    ('a'..'z' | 'A'..'Z');
WS             :    [ \t\r\n]+ ->	channel(HIDDEN);//Whitespace declaration	
fragment DIGIT :    ('0'..'9');
CHR            :    '\''(LETTER|DIGIT|' '|EXC|'"'|'#'|'$'|'%'|'^'|'&'|'*'|LPARENT|RPARENT|PLUS|'_'|MINUS|'?'|'\''|'.'|','|'<'|'>'|':'|';'|'`'|'~'|'@'|'\"') '\''; 
ID             :    LETTER(DIGIT|LETTER)*;
NUM            :    (DIGIT)+;
STRING         :    '\"'(LETTER|DIGIT|' '|EXC|'"'|'#'|'$'|'%'|'^'|'&'|'*'|LPARENT|RPARENT|PLUS|'_'|MINUS|'?'|'\''|'.'|','|'<'|'>'|':'|';'|'`'|'~'|'@'|'\"'|[\s])+'\"';
COMMENT        :    '//'(~('\r'|'\n'))*{skip();};
COMA           :    ','; 
AND            :    '&&';
OR             :    '||';
LBRACE         :    '{';
RBRACE         :    '}';
DOTCOMMA       :    ';';
RCORCH         :    ']';
LCORCH         :    '[';
LPARENT        :    '(';
RPARENT        :    ')';
EQ             :    '=';
DOT            :    '.';
PLUS           :    '+';
MINUS          :    '-';
EXC            :    '!';
AST            :    '*';
SLSH           :    '/';
PRCNT          :    '%';
MTHAN          :    '>';
LTHAN          :    '<';
EQMTHAN        :    '>=';
EQLTHAN        :    '<=';
EQEQ           :    '==';
NOTEQ          :    '!=';

// PRODUCTIONS

program             : CLASS PROGRAM ID LBRACE (declaration)* RBRACE     # programProduction
                        ;

declaration         : structDeclaration
                        | varDeclaration
                        | methodDeclaration
                        ;

varDeclaration      : varType ID DOTCOMMA       # regularVariableProduction
                        | varType ID LCORCH NUM RCORCH DOTCOMMA     # arrayVariableProduction
                        ;

structDeclaration   : STRUCT ID LBRACE (varDeclaration)* RBRACE
                        ;

varType             : (INT | CHAR | BOOLEAN | (STRUCT ID) | structDeclaration | VOID)
                        ;
methodDeclaration   : methodType ID LPARENT (parameter(COMA parameter)*)? RPARENT block  # methodDeclarationProduction
                        ;

methodType          : INT 
                        | CHAR 
                        | BOOLEAN 
                        | VOID
                        ;

parameter           : parameterType ID      # parameterRegularDeclaration
                        | parameterType ID LCORCH RCORCH        # parameterArrayDeclaration
                        ;
parameterType       : INT 
                        | CHAR 
                        | BOOLEAN 
                        ;
block               : LBRACE (varDeclaration)* (statement)* RBRACE 
                        ;

statement           : myIf 
                        | returnBlock 
                        | whileBlock 
                        | methodCall DOTCOMMA 
                        | assignation 
                        | expression DOTCOMMA 
                        | print
                        ;


assignation         : location EQ (expression ) DOTCOMMA        # assignationProduction
                        ;
whileBlock          :  WHILE LPARENT expression RPARENT block      # whileBlockProduction
                        ;
returnBlock         : RETURN (nExpression) DOTCOMMA         # returnBlockProduction
                        ;


print               : PRINT LPARENT ( STRING | location ) RPARENT DOTCOMMA
                        ;


myIf                : IF LPARENT expression RPARENT block(ELSE block)?      #ifProduction
                        ;
location            : declaredVariable 
                        | dotLocation
                        ;
dotLocation         : variable ( DOT location) 
                        | arrayVariable ( DOT location)
                        ;
declaredVariable    : variable 
                        | arrayVariable
                        ;
variable            : ID        # declaredVariableProduction
                        ;
arrayVariable       : ID LCORCH expression RCORCH       # declaredArrayProduction
                        ;
expressionInP       : LPARENT expression RPARENT 
                        ;


nExpression         : expression | 
                        ;
expression          : andExpression  # andExpr
                        | expression OR andExpression   # orProduction
                        ;
andExpression       : equalsExpression # equalsExpr
                        | andExpression AND equalsExpression # andProduction
                        ;
equalsExpression    : relationExpression  # relationExpr
                        | equalsExpression eq_op relationExpression  # equalsProduction
                        ;
relationExpression  : addSubsExpression  #addSubsExpr
                        | relationExpression rel_op addSubsExpression # relationProduction
                        ;
addSubsExpression   : mulDivExpression  # mulDivExpr
                        | addSubsExpression arith_op mulDivExpression  # addSubProduction
                        ;
mulDivExpression    : prExpression # prExpr
                        | mulDivExpression md_op prExpression # mulDivProduction
                        ;
prExpression        : basicExpression 
                        | prExpression pr_op basicExpression
                        ;
basicExpression     : LPARENT (INT|CHAR) RPARENT basic 
                        | MINUS basic 
                        | EXC basic 
                        | basic
                        ;
basic               : expressionInP 
                        | location 
                        | methodCall 
                        | literal
                        ;
arg                 : expression
                        ;


methodCall          : ID LPARENT (arg(COMA arg)*)? RPARENT  #methodCallProduction
                        ;


arith_op            : PLUS | MINUS
                        ;
md_op               : AST | SLSH 
                        ;
pr_op               : PRCNT
                        ;
rel_op              : LTHAN | MTHAN | EQLTHAN | EQMTHAN 
                        ;
eq_op               : EQEQ | NOTEQ
                        ;
cond_op             : AND | OR
                        ;

literal             : int_literal | char_literal | bool_literal
                        ;

int_literal         : NUM
                        ;

char_literal        : CHR
                        ;

bool_literal        : TRUE | FALSE
                        ;
