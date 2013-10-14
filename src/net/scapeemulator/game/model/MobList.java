package net.scapeemulator.game.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.scapeemulator.game.model.mob.Mob;

public final class MobList<T extends Mob> implements Iterable<T> {

	private final Mob[] mobs;
	private int size = 0;

	private class MobListIterator implements Iterator<T> {

		private int index = 0;

		@Override
		public boolean hasNext() {
			for (int i = index; i < mobs.length; i++) {
				if (mobs[i] != null)
					return true;
			}

			return false;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T next() {
			for (; index < mobs.length; index++) {
				if (mobs[index] != null)
					return (T) mobs[index++];
			}

			throw new NoSuchElementException();
		}

		@SuppressWarnings("unchecked")
		@Override
		public void remove() {
			if (index == 0 || mobs[index - 1] == null)
				throw new IllegalStateException();

			MobList.this.remove((T) mobs[index - 1]);
		}

	}

	public MobList(int capacity) {
		mobs = new Mob[capacity];
	}

	public boolean add(T mob) {
		for (int id = 0; id < mobs.length; id++) {
			if (mobs[id] == null) {
				mobs[id] = mob;
				size++;

				mob.setId(id + 1);
				return true;
			}
		}

		return false;
	}
        
        public T get(int index) {
            if(index <= 0 || index >= mobs.length + 1) {
                return null;
            }
            
            return (T) mobs[index - 1];
        }

	public void remove(T mob) {
		int id = mob.getId();
		assert id != 0;

		id--;
		assert mobs[id] == mob;

		mobs[id] = null;
		size--;

		mob.resetId();
	}

	@Override
	public Iterator<T> iterator() {
		return new MobListIterator();
	}

}
