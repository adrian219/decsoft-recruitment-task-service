package pl.com.decsoft.domain.addressbook.photo;

import com.google.common.io.ByteStreams;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

@Service
@RequiredArgsConstructor
public class LobHelper {

  public static class BlobData {

    private final Blob blob;
    private final Long size;

    public BlobData(Blob blob, Long size) {
      this.blob = blob;
      this.size = size;
    }

    public Blob getBlob() {
      return blob;
    }

    public Long getSize() {
      return size;
    }
  }

  private final EntityManager em;

  public BlobData createBlob(InputStream input, long length) throws IOException {
    if (length < 0) {
      File file = File.createTempFile("upload-", null);
      FileOutputStream output = new FileOutputStream(file);

      length = ByteStreams.copy(input, output);
      input.close();
      output.close();

      input = new FileInputStream(file);
    }

    Session session = em.unwrap(Session.class);
    Blob blob = Hibernate.getLobCreator(session).createBlob(input, length);

    return new BlobData(blob, length);
  }

  public BlobData createBlob(byte[] file) {
    Session session = em.unwrap(Session.class);
    Blob blob = Hibernate.getLobCreator(session).createBlob(file);

    return new BlobData(blob, (long) file.length);
  }
}
