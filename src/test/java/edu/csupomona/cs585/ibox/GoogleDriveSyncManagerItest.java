package edu.csupomona.cs585.ibox;

import java.io.File;
import org.junit.BeforeClass;

import com.google.api.services.drive.Drive;
import org.junit.*;

import edu.csupomona.cs585.ibox.sync.GoogleDriveFileSyncManager;
import edu.csupomona.cs585.ibox.sync.GoogleDriveServiceProvider;
import junit.framework.TestCase;

public class GoogleDriveSyncManagerItest extends TestCase{
	GoogleDriveFileSyncManager googleDFS;
	Drive service;
	File dFile;

	@BeforeClass
	public void setUp() {
		service = GoogleDriveServiceProvider.get().getGoogleDriveClient();
		googleDFS = new GoogleDriveFileSyncManager(service);
		dFile = new File("C:\\Users\\kalya\\Desktop\\myself.txt");

	}
	
	@Test
	public void testAddFile() throws Exception {
		googleDFS.addFile(dFile);
		String s = googleDFS.getFileId("myself.txt");
		assertNotNull(s);
	}
	
	public void testUpdateFile() throws Exception {
		googleDFS.updateFile(dFile);
		String s = googleDFS.getFileId("myself.txt");
		assertNotNull(s);
	}	
	public void testDeleteFile() throws Exception {
		googleDFS.deleteFile(dFile);
		String s = googleDFS.getFileId("myself.txt");
		assertNull(s);
	}

}
