package org.pirkaengine.core.expression;

import static org.junit.Assert.*;

import org.junit.Test;
import org.pirkaengine.core.expression.StringExpression;

public class StringExpressionTest {

      @Test
      public void createOrNull_notStringExpression() {
          assertEquals(null, StringExpression.createOrNull("hoge"));
      }
      
      @Test
      public void createOrNull_singleExp() {
          assertEquals(new StringExpression("%s", new String[]{ "hoge" }), StringExpression.createOrNull("${hoge}"));
      }

      @Test
      public void createOrNull_singleExpAndStr() {
          assertEquals(new StringExpression("%s=hoge", new String[]{ "hoge" }), StringExpression.createOrNull("${hoge}=hoge"));
      }

      @Test
      public void createOrNull_strAndSingleExp() {
          assertEquals(new StringExpression("hoge=%s", new String[]{ "hoge" }), StringExpression.createOrNull("hoge=${hoge}"));
      }

      @Test
      public void createOrNull_double() {
          assertEquals(new StringExpression("%s=%s", new String[]{ "hoge", "huga" }), StringExpression.createOrNull("${hoge}=${huga}"));
      }
      
      @Test(expected=IllegalArgumentException.class)
      public void createOrNull_null() {
          StringExpression.createOrNull(null);
      }
      
}
