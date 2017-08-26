package com.huahouye.nio.main;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Path 接口位于 java.nio.file 包下面，全限定名为 java.nio.file.Path。 一个 Path
 * 实例可以指向一个文件或者一个目录，可以是相对路径也可以是绝对路径。
 * @author huahouye@gmail.com
 *
 */
public class Nio09Path {

	public void main1() {
		// 创建一个 Path 实例
		// 绝对路径
		Path path = Paths.get("c:\\data\\myfile.txt");
		// 相对路径
		Path projects = Paths.get("d:\\data", "projects");
		Path file = Paths.get("d:\\data", "projects\\a-project\\myfile.txt");
		// . 代表当前(代码执行的)目录
		Path currentDir = Paths.get(".");
		System.out.println(currentDir.toAbsolutePath());
		// 如果 . 在 路径字符串的中间位置，就是指向同一个目录（就相当于不加 . 一样）
		// d:\data\projects\a-project
		Path currentDir2 = Paths.get("d:\\data\\projects\\.\\a-project");
		// .. 表示父级目录
		Path parentDir = Paths.get("..");
		// 如果 .. 在路径字符串的中间位置，相当于指向了上一级目录
		// d:\data\projects\another-project
		// a-project后面的 .. 改变了目录指向上一级父目录 project ，然后再参照 父目录project，指向下一级目录another-project
		String path2 = "d:\\data\\projects\\a-project\\..\\another-project";
		Path parentDir2 = Paths.get(path2);

		// Path.normalize() 方法
		// Path接口中的normalize()可以标准化一个路径。标准化意思是解析路径中的. 和 .. 。
		String originalPath = "d:\\data\\projects\\a-project\\..\\another-project";
		Path path1 = Paths.get(originalPath);
		System.out.println("path1 = " + path1);
		Path path3 = path1.normalize();
		System.out.println("path2 = " + path3);
		// 输出
		// path1 = d:\data\projects\a-project\..\another-project
		// path2 = d:\data\projects\another-project
		// 代码开始使用.. 创建Path实例，使用路径字符串创建实例后，并把Path实例打印出来（也就是调用Path.toString()方法）。
		// 调用创建的Path实例后，返回一个新的Path实例，这个新的，标准的Path实例最后也打印出来。
		// 我们可以看到规范化的路径没有包含a-project..多余的部分，移除的部分对于绝对路径是无关紧要的
	}

}
