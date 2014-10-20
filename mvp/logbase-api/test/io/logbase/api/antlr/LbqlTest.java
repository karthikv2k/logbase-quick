package io.logbase.api.antlr;

import static org.junit.Assert.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

public class LbqlTest {

  @Test
  public void parse() {
    CharStream cs = new ANTLRInputStream("Test");
    LbqlLexer lexer = new LbqlLexer(cs);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    LbqlParser parser = new LbqlParser(tokens);
    ParseTree tree = parser.statement();
    assertEquals(tree.toStringTree(parser), "(statement (expr Test))");
  }

}
