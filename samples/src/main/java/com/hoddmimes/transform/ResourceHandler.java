package com.hoddmimes.transform;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ResourceHandler
{
	private  String mResourcePath;
	private  String mResourcePattern;
	private boolean isJar = false;

	public ResourceHandler(String pResourcePath, String pResourcePattern) {
	    mResourcePattern = pResourcePattern;
	    mResourcePath = pResourcePath;

		System.out.println(" class loader: " + this.getClass().getClassLoader().getResource( pResourcePattern) + " resource: " + this.getClass().getResource( pResourcePattern));


		isJar = false;
		try {
			URL tURL = ResourceHandler.class.getResource(pResourcePath);
			if (tURL != null) {
				URI tURI = tURL.toURI();
				isJar = (tURI.getScheme().equals("jar")) ? true : false;
			}
		}
		catch( URISyntaxException e) {
		}
	}


	public List<Path> listResources() throws IOException
	{
		Pattern tFilenamePattern = (mResourcePattern == null) ? Pattern.compile(".*") : Pattern.compile( mResourcePattern);

		ArrayList<Path> tResourcePaths = new ArrayList<>();
		Path tPath;
		try {

			URI tURI = ResourceHandler.class.getResource(mResourcePath).toURI();
			if (tURI.getScheme().equals("jar")) {
				FileSystem tFileSystem = FileSystems.newFileSystem(tURI, Collections.<String, Object>emptyMap());
				tPath = tFileSystem.getPath(mResourcePath);
			} else {
				tPath = Paths.get(tURI);
			}
			Stream<Path> tWalk = Files.walk(tPath, 1);
			Iterator<Path> tItr = tWalk.iterator();
			while( tItr.hasNext()) {
				tPath = tItr.next();
				if ((!Files.isDirectory(tPath)) && (tFilenamePattern.matcher( tPath.getFileName().toString()).find())) {
					tResourcePaths.add( tPath );
				}
			}
		}
		catch( Exception e) {
			e.printStackTrace();
			throw new IOException("Resource path \"" + mResourcePath + "\" is not found" );
		}
		return tResourcePaths;
	}

	public InputStream getResourceAsStream( Path pPath ) throws IOException {
		if (isJar) {
			return getClass().getResourceAsStream(pPath.toString());
		} else {
			return new FileInputStream( pPath.toFile() );
		}
	}

	public void info() {
		System.out.println("Classloader: " + this.getClass().getClassLoader().toString() + " resource: " + this.getClass().getResource("/jsonSchemas").toString());
	}

	public List<String> getResourceAsText( Path pPath ) throws IOException {
		String tLine;
		ArrayList<String> tStrings = new ArrayList<>();
		InputStream tInStream = getResourceAsStream(pPath);
		BufferedReader tReader = new BufferedReader(new InputStreamReader(tInStream));

		while ((tLine = tReader.readLine()) != null) {
			tStrings.add(tLine);
		}
		tInStream.close();
		tReader.close();
		return tStrings;
	}




	private void test() throws IOException{
		List<Path> tList = listResources();
		tList.forEach( f -> {
			System.out.println(f.toString());
		});

		System.out.println("\n\n\n");
		List<String> tStrings = getResourceAsText( tList.get(0));
		tStrings.forEach( s -> {
			System.out.println(s);
		});
	}


	public static void main(String[] args) throws IOException {
		ResourceHandler rw = new ResourceHandler("/jsonSchemat", null);
		try {
			rw.test();
		}
		catch( Exception e) {
			e.printStackTrace();
		}


		/*
		URI uri = ResourceWalker.class.getResource("/jsonSchemat").toURI();
		Path myPath;
		if (uri.getScheme().equals("jar")) {
			FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
			myPath = fileSystem.getPath("/jsonSchemat");
		} else {
			myPath = Paths.get(uri);
		}
		Stream<Path> walk = Files.walk(myPath, 1);
		for (Iterator<Path> it = walk.iterator(); it.hasNext();){
			System.out.println(it.next());
		}
	}

	 */
	}
}
