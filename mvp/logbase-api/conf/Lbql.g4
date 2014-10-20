grammar Lbql;
statement : 	expr command* ;
expr : 			expr ('AND' | 'OR' | 'NOT') expr
				| expr expr
				| predicate
				| TEXT 
				| '(' expr ')'
				;
predicate :		TEXT ('=' | '!=' | '>=' | '<=' | '>' | '<') TEXT ;
command :		'| show' TEXT*
				| '| show' TEXT (',' TEXT)*
				;
TEXT : 			'"' (ESC|.)*? '"' 
				| ~[ \r\n]+
				;
fragment
ESC : 			'\\"' | '\\\\' ; 					
WS : 			[ \t\r\n]+ -> skip ;
