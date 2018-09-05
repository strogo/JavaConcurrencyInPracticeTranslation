package net.jcip.examples.ch_6;

import java.util.List;
import java.util.concurrent.*;

import static net.jcip.examples.ch_5.LaunderThrowable.launderThrowable;

/**
 * Renderer
 * <p/>
 * Using CompletionService to render page elements as they become available
 *
 * @author Brian Goetz and Tim Peierls
 */
public abstract class Renderer {

	private final ExecutorService executor;

	Renderer(ExecutorService executor) {
		this.executor = executor;
	}

	void renderPage(CharSequence source) {
		final List<ImageInfo> info = scanForImageInfo(source);
		CompletionService<ImageData> completionService =
				new ExecutorCompletionService<ImageData>(executor);
		for (final ImageInfo imageInfo : info)
			completionService.submit(new Callable<ImageData>() {
				public ImageData call() {
					return imageInfo.downloadImage();
				}
			});

		renderText(source);

		try {
			for (int t = 0, n = info.size(); t < n; t++) {
				Future<ImageData> f = completionService.take();
				ImageData imageData = f.get();
				renderImage(imageData);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			throw launderThrowable(e.getCause());
		}
	}

	abstract void renderText(CharSequence s);

	abstract List<ImageInfo> scanForImageInfo(CharSequence s);

	abstract void renderImage(ImageData i);

	interface ImageData {
	}

	interface ImageInfo {
		ImageData downloadImage();
	}

}
