package io.logbase.api.antlr;

import static org.junit.Assert.assertEquals;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

public class LbqlSqlTranslatorTest {

  @Test
  public void translate() {
    String testQuery = "Abishek AND (country.String=India OR pincode.Int=641045) LOGIN 404 | show name city";
    testQuery = "Hello";
    LbqlSqlTranslator translator = TranslatorUtils.translate(testQuery);
    String expectedResult = "SELECT \"RawEvent.String\" FROM \"TEST\".\"TWITTER\" WHERE \"RawEvent.String\" LIKE 'Hello'";
    assertEquals(translator.getSql(), expectedResult);
  }

}
