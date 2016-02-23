package com.example.camel.mina2.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.example.camel.mina2.Counter;

public class TrafficCodec implements ProtocolCodecFactory {

	private Counter counter = new Counter();

	public TrafficCodec() {
		// counter.start();
	}

	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return new ProtocolEncoderAdapter() {

			public void encode(IoSession session, Object message,
					ProtocolEncoderOutput out) throws Exception {
				IoBuffer buffer = IoBuffer.allocate(8)
						.put(((String) message).getBytes()).flip();
				out.write(buffer);
			}
		};
	}

	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return new CumulativeProtocolDecoder() {

			@Override
			protected boolean doDecode(IoSession session, IoBuffer in,
					ProtocolDecoderOutput out) throws Exception {
				if (in.remaining() < 3) {
					return false;
				}
				byte[] b = new byte[3];
				in.get(b);
				out.write(new String(b));
				// counter.add();
				return true;
			}
		};
	}
}
