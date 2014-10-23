package io.logbase.api.antlr;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class TranslatorUtils {

  public static LbqlSqlTranslator translate(String query) {
    CharStream cs = new ANTLRInputStream(query);
    LbqlLexer lexer = new LbqlLexer(cs);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    LbqlParser parser = new LbqlParser(tokens);
    ParseTree tree = parser.statement();
    ParseTreeWalker walker = new ParseTreeWalker();
    LbqlSqlTranslator translator = new LbqlSqlTranslator();
    walker.walk(translator, tree);
    return translator;
  }

}
