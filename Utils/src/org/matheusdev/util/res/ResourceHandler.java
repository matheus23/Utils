/*
 * Copyright (c) 2012 matheusdev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.matheusdev.util.res;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author matheusdev
 *
 */
public abstract class ResourceHandler<Sheet extends SpriteSheetResource<Sprite>, Sprite extends SpriteResource> {

	protected class ResourceParser extends DefaultHandler {

		protected ResourceHandler<Sheet, Sprite> resources;
		protected Sheet currentSheet;
		protected boolean isRes = false;

		protected ResourceParser(ResourceHandler<Sheet, Sprite> handler) {
			this.resources = handler;
		}

		@Override
		public void startElement(String uri, String localName, String tag, Attributes attributes) throws SAXException {
			switch (tag.toLowerCase()) {
			case "sheet":
				if (isRes) {
					String name = null;
					String source = null;
					// Search for "name" and "sources" attributes and assign them to "name" and "source":
					name = attributes.getValue("name");
					source = attributes.getValue("source");
					// If one of the attributes is missing, output an error and abort:
					if (name == null) {
						System.err.println("Error: name was not given to the SpriteSheet. Discarded.");
						return;
					}
					if (source == null) {
						System.err.println("Error: source was not given to the SpriteSheet. Discarded.");
						return;
					}
					// Let the sub-class load the SpriteSheet and add it to the HashMap:
					currentSheet = resources.createSheet(
							Thread.currentThread().getContextClassLoader().getResourceAsStream(source));
					resources.put(name, currentSheet);
				}
				break;
			case "sprite":
				if (isRes) {
					// If we are inside a <sheet>-tag currently:
					if (currentSheet != null) {
						// Sprite tags should look like this:
						// <sprite
						//     name="grass_block"
						//     bounds="0,0,16,16">
						// </sprite>
						String name = null;
						int x = 0;
						int y = 0;
						int w = 0;
						int h = 0;

						// Search for the attributes "name" and "bounds":
						name = attributes.getValue("name");
						// Bounds should be in the from "X,Y,W,H", example:
						// bounds="0,0,16,16"
						String[] bounds = attributes.getValue("bounds").split(",");
						if (bounds.length != 4) {
							System.err.println("Error: \"bounds\" invalid. Should be in form: X,Y,W,H - Sheet is discarded.");
							return;
						} else {
							// Throw an error if one of the given bound values
							// is not a number:
							try {
								x = Integer.parseInt(bounds[0]);
								y = Integer.parseInt(bounds[1]);
								w = Integer.parseInt(bounds[2]);
								h = Integer.parseInt(bounds[3]);
							} catch (NumberFormatException e) {
								System.err.println("Error: \"bounds\" invalid. Values aren't Integers. Sheet is discarded.");
								return;
							}
						}
						// If the name is missing, output an error:
						if (name == null) {
							System.err.println("Error: Name is missing for Sprite. Discarded.");
							return;
						}
						// If the sizes are invalid, output an error too:
						if (x < 0 || y < 0 || w <= 0 || h <= 0) {
							System.err.println("Error: The sprite's [name: " + name + "] bounds [" + x + "," + y + "," + w + "," + h + "] are invalid or unset. Discarded");
							return;
						}
						currentSheet.put(name, resources.createSprite(currentSheet, x, y, w, h));
					} else {
						System.out.println("Warning: One Sprite is not inside a sheet tag. Discarded.");
					}
				}
				break;
			case "images":
				isRes = true;
				break;
			default:
				if (isRes) {
					System.out.println("Warning: Unknown tag in xml file: " + tag);
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String tag) throws SAXException {
			switch (tag.toLowerCase()) {
			case "sheet":
				currentSheet = null;
				break;
			case "images":
				isRes = false;
				break;
			}
		}
	}

	protected final HashMap<String, Sheet> sheets;

	public ResourceHandler() {
		sheets = new HashMap<>();
	}

	protected abstract Sheet createSheet(InputStream stream);
	protected abstract Sprite createSprite(Sheet sheet, int x, int y, int w, int h);

	public void load(InputStream xmlInput) throws SAXException, IOException, ParserConfigurationException {
		InputSource is = getUTF8Source(xmlInput);
		ResourceParser handler = new ResourceParser(this);
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(is, handler);
	}

	protected InputSource getUTF8Source(InputStream stream) {
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(stream, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			reader = new InputStreamReader(stream);
		}
		InputSource is = new InputSource(reader);
		is.setEncoding("UTF-8");
		return is;
	}

	public void put(String name, Sheet sheet) {
		sheets.put(name, sheet);
	}

	public Sheet get(String name) {
		return sheets.get(name);
	}

}
