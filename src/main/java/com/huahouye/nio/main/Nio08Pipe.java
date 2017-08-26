package com.huahouye.nio.main;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * Java NIO 管道是 2 个线程之间的单向数据连接。Pipe 有一个 source 通道和一个 sink 通道。 数据会被写到 sink 通道，从 source
 * 通道读取。
 * @author huahouye@gmail.com
 *
 */
public class Nio08Pipe {

	public void main1() throws Exception {
		// 打开管道
		Pipe pipe = Pipe.open();

		// 向管道写数据
		// 要向管道写数据，需要访问sink通道
		Pipe.SinkChannel sinkChannel = pipe.sink();
		String newData = "New String to write to channel ...";
		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();
		buf.put(newData.getBytes());
		buf.flip();
		while (buf.hasRemaining()) {
			// 通过调用 SinkChannel 的 write() 方法，将数据写入 SinkChannel
			sinkChannel.write(buf);
		}

		// 从管道读取数据
		// 从读取管道的数据，需要访问source通道
		Pipe.SourceChannel sourceChannel = pipe.source();
		buf.clear();
		// 调用 source 通道的 read() 方法来读取数据,返回的 int 值会告诉我们多少字节被读进了缓冲区。
		int bytesRead = sourceChannel.read(buf);
	}

}
