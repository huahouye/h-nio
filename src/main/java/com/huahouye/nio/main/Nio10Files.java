package com.huahouye.nio.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

public class Nio10Files {

	public void main1() throws Exception {
		Path path = Paths.get("data/logging.properties");
		// Files.exists()
		boolean pathExists = Files.exists(path,
				new LinkOption[] { LinkOption.NOFOLLOW_LINKS });

		// Files.createDirectory()
		Path subdir = Paths.get("data/subdir");
		try {
			Path newDir = Files.createDirectories(subdir);
		}
		catch (FileAlreadyExistsException e) {
			// the directory already exists.
		}
		catch (IOException e) {
			// something else went wrong
			e.printStackTrace();
		}

		// Files.copy()
		Path sourcePath = Paths.get("data/logging.properties");
		Path destinationPath = Paths.get("data/logging-copy.properties");
		try {
			// StandardCopyOption.REPLACE_EXISTING 覆盖
			Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
		}
		catch (FileAlreadyExistsException e) {
			// destination file already exists
		}
		catch (IOException e) {
			// something else went wrong
			e.printStackTrace();
		}

		// Files.move()
		Path sourcePath2 = Paths.get("data/logging-copy.properties");
		Path destinationPath2 = Paths.get("data/subdir/logging-moved.properties");
		try {
			Files.move(sourcePath2, destinationPath2,
					StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e) {
			// moving file failed.
			e.printStackTrace();
		}

		// Files.delete()
		Path delPath = Paths.get("data/subdir/logging-moved.properties");
		try {
			Files.delete(path);
		}
		catch (IOException e) {
			// deleting file failed
			e.printStackTrace();
		}

		// Files.walkFileTree() 递归遍历目录树，需要自己实现 FileVisitor 接口
		Files.walkFileTree(path, new FileVisitor<Path>() {
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
					throws IOException {
				System.out.println("pre visit dir:" + dir);
				return FileVisitResult.CONTINUE;
			}

			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
					throws IOException {
				System.out.println("visit file: " + file);
				return FileVisitResult.CONTINUE;
			}

			public FileVisitResult visitFileFailed(Path file, IOException exc)
					throws IOException {
				System.out.println("visit file failed: " + file);
				return FileVisitResult.CONTINUE;
			}

			public FileVisitResult postVisitDirectory(Path dir, IOException exc)
					throws IOException {
				System.out.println("post visit directory: " + dir);
				return FileVisitResult.CONTINUE;
			}
		});

		// Searching For Files 查找文件
		Path rootPath = Paths.get("data");
		final String fileToFind = File.separator + "README.txt";
		try {
			Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
						throws IOException {
					String fileString = file.toAbsolutePath().toString();
					// System.out.println("pathString = " + fileString);

					if (fileString.endsWith(fileToFind)) {
						System.out
								.println("file found at path: " + file.toAbsolutePath());
						return FileVisitResult.TERMINATE;
					}
					return FileVisitResult.CONTINUE;
				}
			});
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		// Deleting Directories Recursively 递归删除文件
		Path rootPath2 = Paths.get("data/to-delete");

		try {
			Files.walkFileTree(rootPath2, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
						throws IOException {
					System.out.println("delete file: " + file.toString());
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc)
						throws IOException {
					Files.delete(dir);
					System.out.println("delete dir: " + dir.toString());
					return FileVisitResult.CONTINUE;
				}
			});
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
