package pl.com.decsoft.domain.addressbook.photo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PhotoValidatorTest {

  static PhotoValidator photoValidator;

  @BeforeAll
  static void before() {
    photoValidator = new PhotoValidator();
  }

  @Test
  void test1() {
    String extension = "png";

    boolean validate = photoValidator.validate(extension);

    assertTrue(validate);
  }

  @Test
  void test2() {
    String extension = ".jpg";

    boolean validate = photoValidator.validate(extension);

    assertTrue(validate);
  }

  @Test
  void test3() {
    String extension = "jpeg";

    boolean validate = photoValidator.validate(extension);

    assertTrue(validate);
  }

  @Test
  void test4() {
    String extension = ".jpeg";

    boolean validate = photoValidator.validate(extension);

    assertTrue(validate);
  }

  @Test
  void test5() {
    String extension = ".pdf";

    boolean validate = photoValidator.validate(extension);

    assertFalse(validate);
  }

  @Test
  void test6() {
    String extension = ".tiff";

    boolean validate = photoValidator.validate(extension);

    assertFalse(validate);
  }

  @Test
  void test7() {
    MockMultipartFile file = new MockMultipartFile("obrazek.jpg", "obrazek.jpg",
        MediaType.IMAGE_PNG.toString(), new byte[0]);

    boolean validate = photoValidator.validate(file);

    assertTrue(validate);
  }

  @Test
  void test8() {
    MockMultipartFile file = new MockMultipartFile("obrazek2.pdf", "obrazek2.pdf",
        MediaType.APPLICATION_PDF.toString(), new byte[0]);

    boolean validate = photoValidator.validate(file);

    assertFalse(validate);
  }

  @Test
  void test9() {
    MockMultipartFile file = null;

    boolean validate = photoValidator.validate(file);

    assertFalse(validate);
  }

  @Test
  void test10() {
    String extension = null;

    boolean validate = photoValidator.validate(extension);

    assertFalse(validate);
  }

  @Test
  void test11() {
    String extension = "PNG";

    boolean validate = photoValidator.validate(extension);

    assertTrue(validate);
  }

  @Test
  void test12() {
    String extension = ".JPG";

    boolean validate = photoValidator.validate(extension);

    assertTrue(validate);
  }

  @Test
  void test13() {
    String extension = "JPEG";

    boolean validate = photoValidator.validate(extension);

    assertTrue(validate);
  }
}