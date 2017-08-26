package com.huahouye.nio.main;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 分散（scatter）从Channel中读取是指在读操作时将读取的数据写入多个buffer中。因此，
 * Channel将从Channel中读取的数据“分散（scatter）”到多个Buffer中。
聚集（gather）写入Channel是指在写操作时将多个buffer的数据写入同一个Channel，因此，
Channel 将多个Buffer中的数据“聚集（gather）”后发送到Channel。

scatter / gather经常用于需要将传输的数据分开处理的场合，例如传输一个由消息头和消息体
组成的消息，你可能会将消息体和消息头分散到不同的buffer中，这样你可以方便的处理消息头和消
息体。
 * @author huahouye@gmail.com
 *
 */
public class Nio02ScatterGather {
	public static void main(String[] args) {
		try {
			RandomAccessFile aFile = new RandomAccessFile("data/nio-scatter.txt", "rw");
			FileChannel channel = aFile.getChannel();
			RandomAccessFile aFile2 = new RandomAccessFile("data/nio-gather.txt", "rw");
			FileChannel channel2 = aFile.getChannel();

			/**
			 *  Scattering Reads
			 *  Scattering Reads在移动下一个buffer前，必须填满当前的buffer，
			 *  这也意味着它不适用于动态消息(译者注：消息大小不固定)。换句话说，如果
			 *  存在消息头和消息体，消息头必须完成填充（例如 128byte），Scattering
			 *  Reads才能正常工作。
			 */
			ByteBuffer header = ByteBuffer.allocate(128);
			ByteBuffer body = ByteBuffer.allocate(1024);
			// buffer首先被插入到数组，然后再将数组作为channel.read() 的输入参数。
			ByteBuffer[] bufferArray = {header, body};
			// read()方法按照buffer在数组中的顺序将从channel中读取的数据
			// 写入到buffer，当一个buffer被写满后，channel紧接着向另一个buffer中写。
			channel.read(bufferArray);
			
			/**
			 * buffers数组是write()方法的入参，write()方法会按照buffer在数组中的
			 * 顺序，将数据写入到channel，注意只有position和limit之间的数据才会被写
			 * 入。因此，如果一个buffer的容量为128byte，但是仅仅包含58byte的数据，那
			 * 么这58byte的数据将被写入到channel中。因此与Scattering Reads相反，
			 * Gathering Writes能较好的处理动态消息。
			 */
			channel2.write(bufferArray);
			
			aFile.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
