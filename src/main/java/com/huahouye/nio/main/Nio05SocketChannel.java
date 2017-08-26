package com.huahouye.nio.main;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Java NIO中的SocketChannel是一个连接到TCP网络套接字的通道。可以通过以下2种方式创建SocketChannel：
 * 1、打开一个SocketChannel并连接到互联网上的某台服务器。 2、一个新连接到达ServerSocketChannel时，会创建一个SocketChannel。
 * @author huahouye@gmail.com
 *
 */
public class Nio05SocketChannel {

	public void main1() throws Exception {
		// 打开 SocketChannel
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("http://tutorials.jenkov.com/", 80));
		ByteBuffer buf = ByteBuffer.allocate(48);

		// 从 SocketChannel 读取数据
		// read() 方法返回的 int 值表示读了多少字节进 Buffer 里。如果返回的是 -1，表示已经读到了流的末尾（连接关闭了）。
		int bytesRead = socketChannel.read(buf);

		// 写入 SocketChannel
		String newData = "New String to write fo socket channel ...";
		buf.clear();
		buf.put(newData.getBytes());
		buf.flip();
		// Write() 方法无法保证能写多少字节到 SocketChannel。所以，我们重复调用 write() 直到 Buffer 没有要写的字节为止。
		while (buf.hasRemaining()) {
			socketChannel.write(buf);
		}

		// 关闭 SocketChannel
		socketChannel.close();
	}

	// 非阻塞模式
	// 非阻塞模式与选择器搭配会工作的更好，通过将一或多个 SocketChannel 注册到 Selector，可以询问选择器哪个通道已经准备好了读取，写入等。
	public void main2() throws Exception {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false); // 非阻塞模式
		socketChannel.connect(new InetSocketAddress("http://tutorials.jenkov.com/", 80));
		while (!socketChannel.finishConnect()) {
			// wait, or do something else...
			// 非阻塞模式下，write() 方法在尚未写出任何内容时可能就返回了。所以需要在循环中调用 write()。
			// 非阻塞模式下,read() 方法在尚未读取到任何数据时可能就返回了。所以需要关注它的 int 返回值，它会告诉你读取了多少字节。
		}
	}

}
