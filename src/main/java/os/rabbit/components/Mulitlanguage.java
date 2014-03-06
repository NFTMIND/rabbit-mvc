package os.rabbit.components;

import java.io.PrintWriter;
import java.util.Locale;

import os.rabbit.IRender;
import os.rabbit.modifiers.AttributeModifier;
import os.rabbit.modifiers.BodyModifier;
import os.rabbit.parser.Tag;

public class Mulitlanguage extends Component {

	public Mulitlanguage(Tag tag) {
		super(tag);

	}

	@Override
	protected void afterBuild() {
		final String from = getTag().getAttribute("language:from");
		if (from != null && from.startsWith("attribute")) {
			int start = from.indexOf(":");
			final String attrName = from.substring(start + 1, from.length());
			new AttributeModifier(this, attrName, new IRender() {
				@Override
				public void render(PrintWriter writer) {
					
					String word = getTag().getAttribute(attrName);
					String rs = transalte(word, getLocale());
					if (rs == null) {
						writer.write(word);
						return;
					} else
						writer.write(rs);
				}
			});
			
		} else {
			new BodyModifier(this, new IRender() {
				@Override
				public void render(PrintWriter writer) {
					if (getLocale() == Locale.TAIWAN) {
						writer.write(Mulitlanguage.this.getTag().getBody());
						return;
					}
					try {
						if (getTag().hasBody()) {
							String word = getTag().getTemplate().substring(getTag().getBodyStart(), getTag().getBodyEnd());
							String rs = transalte(word, getLocale());

							if (rs == null) {
								// super.renderComponent(writer);
								writer.write(Mulitlanguage.this.getTag().getBody());
								return;
							} else
								writer.write(rs);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
//		new BodyModifier(this, new IRender() {
//			@Override
//			public void render(PrintWriter writer) {
//
//				if (getSelectedLocale().equals("zh_TW")) {
//					// super.renderComponent(writer);
//					writer.write(Mulitlanguage.this.getTag().getBody());
//					return;
//				}
//				try {
//
//					String from = getTag().getAttribute("language:from");
//					if (from != null && from.startsWith("attribute")) {
//
//						int start = from.indexOf(":");
//						String attrName = from.substring(start + 1, from.length());
//						String word = getTag().getAttribute(attrName);
//						String rs = transalte(word, getSelectedLocale());
//						if (rs == null) {
//							// super.renderComponent(writer);
//							writer.write(Mulitlanguage.this.getTag().getBody());
//							return;
//						} else
//							writer.write(rs);
//					} else {
//
//						if (getTag().hasBody()) {
//							String word = getTag().getTemplate().substring(getTag().getBodyStart(), getTag().getBodyEnd());
//							String rs = transalte(word, getSelectedLocale());
//
//							if (rs == null) {
//								// super.renderComponent(writer);
//								writer.write(Mulitlanguage.this.getTag().getBody());
//								return;
//							} else
//								writer.write(rs);
//						}
//
//					}
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

}
