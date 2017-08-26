package com.huahouye.nio.main;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Nio06ServerSocketChannel {

	public void main1() throws Exception {
		ServerSocketChannel serverSocketChannel = null;
		// 打开 ServerSocketChannel
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(9999));
		while (true) {
			// 监听新进来的连接,accept() 方法会一直阻塞到有新连接到达
			SocketChannel socketChannel = serverSocketChannel.accept();
			// do something with socketChannel...
		}
		// 关闭 ServerSocketChannel
		// serverSocketChannel.close();

	}

	// 非阻塞模式
	// ServerSocketChannel 可以设置成非阻塞模式。在非阻塞模式下，accept() 方法会立刻返回，
	// 如果还没有新进来的连接,返回的将是 null。 因此，需要检查返回的 SocketChannel 是否是 null.
	public void main2() throws Exception {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		serverSocketChannel.socket().bind(new InetSocketAddress(9999));
		serverSocketChannel.configureBlocking(false);

		while (true) {
			SocketChannel socketChannel = serverSocketChannel.accept();

			if (socketChannel != null) {
				// do something with socketChannel...
			}
		}
	}

}
