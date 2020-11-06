package pl.com.decsoft.domain.addressbook.photo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileNameParserTest {

  @Test
  void test1() {
    //given
    String filename = "obrazke.jpg";

    //when
    String name = FileNameParser.getFilename(filename);
    String extension = FileNameParser.getFileExtension(filename);

    //then
    assertEquals("obrazke", name);
    assertEquals("jpg", extension);
  }

  @Test
  void test2() {
    //given
    String filename = "inn_obraz.pneg";

    //when
    String name = FileNameParser.getFilename(filename);
    String extension = FileNameParser.getFileExtension(filename);

    //then
    assertEquals("inn_obraz", name);
    assertEquals("pneg", extension);
  }

  @Test
  void test3() {
    //given
    String filename = null;

    //when
    String name = FileNameParser.getFilename(filename);
    String extension = FileNameParser.getFileExtension(filename);

    //then
    assertNull(name);
    assertNull(extension);
  }

  @Test
  void test4() {
    //given
    String filename = "obrazke";

    //when
    String name = FileNameParser.getFilename(filename);
    String extension = FileNameParser.getFileExtension(filename);

    //then
    assertEquals("obrazke", name);
    assertNull(extension);
  }
}