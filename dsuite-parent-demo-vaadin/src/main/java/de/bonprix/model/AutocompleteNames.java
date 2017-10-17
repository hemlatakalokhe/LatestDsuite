/*
 * Copyright 2015 Max Schuster.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.bonprix.model;

/**
 * Contains a list of Names to show Autocomlete in TextField
 * 
 * @author Torben Meißner
 */
public class AutocompleteNames {

	/**
	 * A list of Names
	 */
	private static final String[] names = new String[] { "Mia", "Emma", "Sofia", "Sophia", "Hannah ", "Hanna", "Emilia",
			"Anna", "Marie", "Mila", "Lina", "Lea  ", "Leah", "Lena", "Leonie", "Amelie", "Luisa ", "Louisa", "Johanna",
			"Emily ", "Emilie", "Clara ", "Klara", "Sophie ", "Sofie", "Charlotte", "Lilly ", "Lilli", "Lara", "Laura",
			"Leni", "Nele", "Neele", "Ella", "Maja ", "Maya", "Mathilda ", "Matilda", "Ida", "Frieda ", "Frida", "Lia ",
			"Liah ", "Lya", "Greta", "Sarah ", "Sara", "Lotta", "Pia", "Julia", "Melina", "Paula", "Alina", "Marlene",
			"Elisa", "Lisa", "Mira", "Victoria", "Viktoria", "Anni", "Annie", "Anny", "Nora", "Mara ", "Marah",
			"Isabell", "Isabel", "Isabelle", "Helena", "Isabella", "Maria", "Ben", "Paul", "Jonas", "Elias", "Leon",
			"Finn", "Fynn", "Noah", "Luis", "Louis", "Lukas", "Lucas", "Felix", "Luca", "Luka", "Maximilian", "Henry",
			"Henri", "Max", "Oskar", "Oscar", "Emil", "Liam", "Jakob", "Jacob", "Moritz", "Anton", "Julian", "Theo",
			"Niklas", "Niclas", "David", "Philipp", "Alexander", "Tim", "Matteo", "Milan", "Leo", "Tom", "Mats", "Mads",
			"Carl", "Karl", "Erik", "Eric", "Linus", "Jonathan", "Jan", "Fabian", "Leonard", "Samuel", "Rafael",
			"Raphael", "Jona ", "Jonah", "Jannik", "Yannik", "Yannick", "Yannic", "Simon", "Vincent", "Mika", "Hannes",
			"Lennard", "Lennart", "Till", "Aaron" };

	private AutocompleteNames() {
	}

	public static String[] getNames() {
		return AutocompleteNames.names;
	}
}
