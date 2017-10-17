package de.bonprix;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.bonprix.dto.Identifiable;

public class EntityCollection<E extends Identifiable> implements Iterable<E> {

	private final Map<Long, Identifiable> values = new HashMap<>();

	public EntityCollection() {
		// Empty constructor
	}

	public EntityCollection(final Collection<E> initialValues) {
		addAll(initialValues);
	}

	@Override
	public Iterator<E> iterator() {
		return null;
	}

	public int size() {
		return this.values.size();
	}

	public boolean isEmpty() {
		return this.values.isEmpty();
	}

	public boolean containsId(final Long id) {
		return this.values.containsKey(id);
	}

	public boolean containsEntity(final E entity) {
		return this.values.containsKey(entity);
	}

	public void add(final E e) {
		this.values.put(e.getId(), e);
	}

	public Identifiable remove(final E entity) {
		return this.values.remove(entity.getId());
	}

	public void addAll(final Collection<? extends E> c) {
		for (final E e : c) {
			add(e);
		}
	}

	public void removeAll(final Collection<E> c) {
		for (final E e : c) {
			remove(e);
		}
	}

	public void removeByKey(final Collection<Long> c) {
		for (final Long id : c) {
			this.values.remove(id);
		}
	}

	public void clear() {
		this.values.clear();
	}

	public E get(final Long id) {
		return (E) this.values.get(id);
	}
}
