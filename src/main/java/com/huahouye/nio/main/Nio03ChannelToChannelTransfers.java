package com.huahouye.nio.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 在 Java NIO 中，如果两个通道中有一个是 FileChannel，那你可以直接将数据从 一个 channel 传输到另外一个 channel。
 * @author huahouye@gmail.com
 *
 */
public class Nio03ChannelToChannelTransfers {

	public void main1() throws Exception {
		RandomAccessFile fromFile = new RandomAccessFile("data/fromFile.txt", "rw");
		FileChannel fromChannel = fromFile.getChannel();

		RandomAccessFile toFile = new RandomAccessFile("data/toFile.txt", "rw");
		FileChannel toChannel = toFile.getChannel();

		long position = 0;
		long count = fromChannel.size();

		/**
		 * 方法的输入参数 position 表示从 position 处开始向目标文件写入数据，count 表示最多传输的字节数。 如果源通道的剩余空间小于 count
		 * 个字节，则所传输的字节数要小于请求的字节数。 此外要注意，在 SoketChannel 的实现中，SocketChannel
		 * 只会传输此刻准备好的数据（可能不足 count 字节）。因此， SocketChannel 可能不会将请求的所有数据(count 个字节)全部传输到
		 * FileChannel 中。
		 */
		toChannel.transferFrom(fromChannel, position, count);

		/**
		 * transferTo() 方法将数据从 FileChannel 传输到其他的 channel 中,上面所说的关于 SocketChannel 的问题在
		 * transferTo() 方法中同样存在。SocketChannel 会一直传输数据直到目标 buffer 被填满。
		 */
		fromChannel.transferTo(position, count, toChannel);

		fromFile.close();
		toFile.close();
	}

}
