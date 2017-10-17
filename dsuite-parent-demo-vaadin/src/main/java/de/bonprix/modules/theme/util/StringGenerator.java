package de.bonprix.modules.theme.util;

public class StringGenerator {

	static String[] strings = new String[] { "lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "quid", "securi",
			"etiam", "tamquam", "eu", "fugiat", "nulla", "pariatur" };
	int stringCount = -1;

	public String nextString(final boolean capitalize) {
		if (++this.stringCount >= strings.length) {
			this.stringCount = 0;
		}
		return capitalize ? strings[this.stringCount]	.substring(0, 1)
														.toUpperCase()
				+ strings[this.stringCount].substring(1) : strings[this.stringCount];
	}

}
