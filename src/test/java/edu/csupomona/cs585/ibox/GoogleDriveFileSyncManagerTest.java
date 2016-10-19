package edu.csupomona.cs585.ibox;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.BeforeClass;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Files.Delete;
import com.google.api.services.drive.Drive.Files.Insert;
import com.google.api.services.drive.Drive.Files.List;
import com.google.api.services.drive.Drive.Files.Update;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import edu.csupomona.cs585.ibox.sync.GoogleDriveFileSyncManager;
import junit.framework.TestCase;

public class GoogleDriveFileSyncManagerTest extends TestCase {

	GoogleDriveFileSyncManager googleDFS;
	Drive service;

	@BeforeClass
	public void setUp() {
		service = mock(Drive.class);
		googleDFS = new GoogleDriveFileSyncManager(service);

	}

	public void testAddFile() throws IOException {
		java.io.File localFile = mock(java.io.File.class);
		File body = new File();
		Files files = mock(Files.class);
		Insert insert = mock(Insert.class);

		when(service.files()).thenReturn(files);
		when(files.insert(isA(File.class), isA(AbstractInputStreamContent.class))).thenReturn(insert);
		when(insert.execute()).thenReturn(body);

		googleDFS.addFile(localFile);

		verify(service).files();
		verify(files).insert(isA(File.class), isA(AbstractInputStreamContent.class));
		verify(insert).execute();
	}

	// update file
	public void testCUpdateFile() throws IOException {
		java.io.File localFile = mock(java.io.File.class); // mocking local
															// files
		ArrayList<File> arrayListFile = new ArrayList<File>(); // create empty
																// list
		List list = mock(List.class); //
		FileList fileList = new FileList(); // creating new file list for local
											// files
		File body = new File(); // new file
		File sBody = new File(); // new file to get output

		body.setTitle("test1.txt"); // name for NEW FILE
		body.setId("TT001"); // giving an Id
		arrayListFile.add(body); // add body to ALIST FILE
		fileList.setItems(arrayListFile); // add afileList to fileList

		//
		Files files = mock(Files.class); // mock Files
		Update update = mock(Update.class); // mock update function

		when(localFile.getName()).thenReturn("test1.txt"); // go for local file
															// and get name file
		when(files.list()).thenReturn(list); // when we execute List we return
												// FileList
		when(list.execute()).thenReturn(fileList); // when you execute list
													// return to fileList

		when(service.files()).thenReturn(files);
		when(files.update(isA(String.class), isA(File.class), isA(AbstractInputStreamContent.class)))
				.thenReturn(update);
		when(update.execute()).thenReturn(sBody);

		googleDFS.updateFile(localFile);

		verify(localFile, atLeast(1)).getName();
		verify(files).list();
		verify(list).execute();
		verify(service, atLeast(1)).files();
		verify(files).update(isA(String.class), isA(File.class), isA(AbstractInputStreamContent.class));
		verify(update).execute();
	}

	// deleteFile
	public void testDeleteFile() throws Exception {
		java.io.File localFile = mock(java.io.File.class); // mocking local
		// files
		ArrayList<File> arrayListFile = new ArrayList<File>(); // create empty
		// list
		List list = mock(List.class); //
		FileList fileList = new FileList(); // creating new file list for local
		// files
		File body = new File(); // new file

		body.setTitle("test1.txt"); // name for NEW FILE
		body.setId("TT001"); // giving an Id
		arrayListFile.add(body); // add body to ALIST FILE
		fileList.setItems(arrayListFile); // add afileList to fileList

		//
		Files files = mock(Files.class); // mock Files
		Delete delete = mock(Delete.class); // mock update function

		when(localFile.getName()).thenReturn("test1.txt"); // go for local file
		// and get name file
		when(files.list()).thenReturn(list); // when we execute List we return
		// FileList
		when(list.execute()).thenReturn(fileList); // when you execute list
		// return to fileList

		when(service.files()).thenReturn(files);
		when(files.delete(isA(String.class)))
				.thenReturn(delete);
		when(delete.execute()).thenReturn(null);

		googleDFS.deleteFile(localFile);

		verify(localFile, atLeast(1)).getName();
		verify(files).list();
		verify(list).execute();
		verify(service, atLeast(1)).files();
		verify(files).delete(isA(String.class));
		verify(delete).execute();

	}

}