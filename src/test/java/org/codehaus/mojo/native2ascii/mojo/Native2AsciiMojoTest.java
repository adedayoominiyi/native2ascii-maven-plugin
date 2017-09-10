/*
 * The MIT License
 * Copyright (c) 2007 The Codehaus
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.codehaus.mojo.native2ascii.mojo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.codehaus.mojo.native2ascii.mojo.Native2AsciiMojo;
import org.junit.Test;


/**
 * @author David Matějček
 */
public class Native2AsciiMojoTest {

  @Test
  public void testFile() throws Exception {
    Native2AsciiMojo mojo = new Native2AsciiMojo();
    mojo.encoding = "UTF-8";
    mojo.srcDir = new File(Native2AsciiMojoTest.class.getClassLoader().getResource("xxx.properties").toURI()).getParentFile();
    mojo.targetDir = mojo.srcDir.getParentFile();
    mojo.includes = new String[] {"xxx.properties"};
    mojo.excludes = new String[0];
    mojo.execute();

    Properties properties = new Properties();
    final FileInputStream inputStream = new FileInputStream(new File(mojo.targetDir, "xxx.properties"));
    try {
      properties.load(inputStream);
    } finally {
      inputStream.close();
    }
    assertEquals("~", properties.get("1"));
    assertEquals("", properties.get("2"));
    assertNull(properties.get("3"));
    assertEquals("粗 Řízeček utíká a řeže zatáčky! §", properties.get("4"));
    assertNull(properties.get("5"));
    assertEquals("more tests needed!", properties.get("6"));
    assertEquals("", properties.get("7"));
  }

}
