package io.logbase.api.antlr;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

public class LbqlSqlTranslatorTest {

  @Test
  public void translate() {
    CharStream cs = new ANTLRInputStream(
        "Abishek AND (country.String=India OR pincode.Int=641045) LOGIN 404 | show name city");
    LbqlLexer lexer = new LbqlLexer(cs);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    LbqlParser parser = new LbqlParser(tokens);
    ParseTree tree = parser.statement();
    System.out.println("Parsed Test expression: " + tree.toStringTree(parser));
    ParseTreeWalker walker = new ParseTreeWalker();
    walker.walk(new LbqlSqlTranslator(), tree);
  }

}
