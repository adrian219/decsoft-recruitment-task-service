package pl.com.decsoft.domain.addressbook.photo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.decsoft.DecsoftRecruitmentTaskServiceApplication;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DecsoftRecruitmentTaskServiceApplication.class)
class BlobStorePhotoServiceTest {

  @Autowired
  private BlobStorePhotoService blobStorePhotoService;

  @Autowired
  private PhotoRepository photoRepository;

  @BeforeEach
  void setUp() {
    photoRepository.deleteAll();
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"ADMIN"})
  void givenFile_whenUpload_thenResultsListInIncreasedByOne() {
    //given
    MockMultipartFile file = new MockMultipartFile("filename.png", "filename.png",
        "application/png", new byte[1]);

    //when
    PhotoEntity upload = blobStorePhotoService.upload(file);

    //then
    assertEquals(1, photoRepository.count());
    assertEquals("filename", upload.getFileName());
    assertEquals("png", upload.getFileExtension());
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"ADMIN"})
  void givenFileWithIncorrectExtension_whenUpload_thenThrowUnsupportedExtensionException() {
    //given
    MockMultipartFile file = new MockMultipartFile("filename.tiff", "filename.tiff",
        "application/tiff", new byte[1]);

    //when and then
    assertThrows(UnsupportedPhotoExtensionException.class, () -> blobStorePhotoService.upload(file));
  }
}