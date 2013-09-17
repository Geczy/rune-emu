package net.scapeemulator.game.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.FileRegion;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import net.scapeemulator.game.net.file.FileProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class HttpChannelHandler extends ChannelInboundMessageHandlerAdapter<HttpRequest> {

	private static final Logger logger = LoggerFactory.getLogger(HttpChannelHandler.class);

	private static final String NOT_FOUND_HTML = "<html><head><title>404 - Page not found</title></head><body style=\"color: black; background: white; font-family: Arial, Verdana, Helvetica;\"><div style=\"font-weight: bold; color: #666666; font-size: large\">404 - Page not found</div><hr width=\"300\" align=\"left\" /><p>Sorry, the page you were looking for was not found.</p><!--Padding for IEPadding Padding for IEPadding Padding for IEPadding Padding for IEPadding for IEPadding for IEPadding for IEPadding for IEPadding for IEPadding for IEPadding for IEPadding for IEPadding for IE--></body></html>";
	private static final FileProvider provider = new FileProvider(false);

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		logger.info("Channel connected: " + ctx.channel().remoteAddress() + ".");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		logger.info("Channel disconnected: " + ctx.channel().remoteAddress() + ".");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.warn("Exception caught, closing channel...", cause);
		ctx.close();
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, HttpRequest request) throws IOException {
		String path = request.getUri();
		FileRegion file = provider.serve(path);
		if (file != null) {
			HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
			response.headers().set("Server", "JAGeX/3.1");
			response.headers().set("Connection", "close");
			response.headers().set("Content-Type", getMimeType(path));
			response.headers().set("Content-Length", file.count());
			ctx.write(response);
			ctx.write(file).addListener(ChannelFutureListener.CLOSE);
		} else {
			ByteBuf buf = Unpooled.wrappedBuffer(NOT_FOUND_HTML.getBytes(StandardCharsets.UTF_8));
			HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.NOT_FOUND);
			response.headers().set("Server", "JAGeX/3.1");
			response.headers().set("Connection", "close");
			response.headers().set("Content-Type", "text/html; charset=utf-8");
			response.headers().set("Content-Length", buf.readableBytes());
			ctx.write(response);
            ctx.write(buf).addListener(ChannelFutureListener.CLOSE);
		}
	}

	private String getMimeType(String path) {
		if (path.equals("/") || path.endsWith(".html"))
			return "text/html; charset=utf-8";
		else if (path.endsWith(".jar"))
			return "application/java-archive";
		else if (path.endsWith("pack200"))
			return "application/x-java-pack200";
		else
			return "application/octet-stream";
	}

}
