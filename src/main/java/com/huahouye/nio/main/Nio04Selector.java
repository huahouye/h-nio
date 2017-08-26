package com.huahouye.nio.main;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Selector（选择器）是 Java NIO 中能够检测一到多个 NIO 通道，并能够知晓通道是否为诸如读写事件做好准备的组件。这样，一个单独的线程可以管理多个
 * channel，从而管理多个网络连接。
 * @author huahouye@gmail.com
 *
 */
public class Nio04Selector {

	public void main1() throws Exception {
		ServerSocketChannel socketChannel = ServerSocketChannel.open();

		// 通过调用 Selector.open() 方法创建一个 Selectorssss
		Selector selector = Selector.open();
		socketChannel.configureBlocking(false);

		/**
		 * 为了将 Channel 和 Selector 配合使用，必须将 channel 注册到 selector 上。通过
		 * SelectableChannel.register() 方法来实现。
		 * 
		 * 与 Selector 一起使用时，Channel 必须处于非阻塞模式下。这意味着不能将 FileChannel 与 Selector 一起使用，因为
		 * FileChannel 不能切换到非阻塞模式。而套接字通道都可以。
		 * 
		 * register() 方法的第二个参数。这是一个"interest集合"，意思是在通过 Selector 监听 Channel
		 * 时对什么事件感兴趣。可以监听四种不同类型的事件：Connect、Accept、Read 和
		 * Write。通道触发了一个事件意思是该事件已经就绪。所以，某个channel成功连接到另一个服务器称为“连接就绪”。一个 server socket
		 * channel 准备好接收新进入的连接称为“接收就绪”。一个有数据可读的通道可以说是“读就绪”。等待写数据的通道可以说是“写就绪”。
		 * 这四种事件用SelectionKey的四个常量来表示：SelectionKey.OP_CONNECT、SelectionKey.OP_ACCEPT、SelectionKey.OP_READ
		 * 和 SelectionKey.OP_WRITE。如果你对不止一种事件感兴趣，那么可以用“位或”操作符将常量连接起来，如：int interestSet =
		 * SelectionKey.OP_READ | SelectionKey.OP_WRITE;
		 * 
		 */
		SelectionKey selectionKey = socketChannel.register(selector,
				SelectionKey.OP_READ);
		while (true) {
			/**
			 * select() 方法返回的 int 值表示有多少通道已经就绪。亦即，自上次调用 select() 方法后有多少通道变成就绪状态。如果调用
			 * select() 方法，因为有一个通道变成就绪状态，返回了 1，若再次调用 select() 方法，如果另一个通道就绪了，它会再次返回
			 * 1。如果对第一个就绪的 channel 没有做任何操作，现在就有两个就绪的通道，但在每次 select() 方法调用之间，只有一个通道就绪了。
			 */
			int readyChannels = selector.select();
			if (readyChannels == 0)
				continue;
			Set selectedKeys = selector.selectedKeys();
			Iterator keyIterator = selectedKeys.iterator();
			while (keyIterator.hasNext()) {
				SelectionKey key = (SelectionKey) keyIterator.next();
				if (key.isAcceptable()) {
					// a connection was accepted by a ServerSocketChannel.
				}
				else if (key.isConnectable()) {
					// a connection was established with a remote server.
				}
				else if (key.isReadable()) {
					// a channel is ready for reading
				}
				else if (key.isWritable()) {
					// a channel is ready for writing
				}
				keyIterator.remove();
			}
		}
	}

}
