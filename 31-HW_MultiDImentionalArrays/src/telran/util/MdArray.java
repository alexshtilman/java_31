package telran.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.lang.reflect.Array;

public class MdArray<T> {
	private MdArray<T>[] array; // array of MdArray objects on one dimension less if array is not null the value
								// should be null
	private T value; // MdArray with dimension 0 - scalar; if value is not null the array should be
						// null

	private MdArray(int[] dimensions, int dimension, T value) {
		if (dimension == dimensions.length) {
			array = null;
			this.value = value;
		} else {
			array = new MdArray[dimensions[dimension]];
			for (int i = 0; i < array.length; i++) {
				array[i] = new MdArray<T>(dimensions, dimension + 1, value);
			}
		}
	}
 
	public MdArray(int[] dimensions, T value) {
		this(dimensions, 0, value);
	}

	/**
	 * performs a given action defined by Consumer functional interface for each
	 * value in multi-dimensional array
	 * 
	 * @param consumer
	 */
	public void forEach(Consumer<T> consumer) {
		if (array == null) {
			consumer.accept(value);
		} else {
			for (MdArray<T> x : array) {
				x.forEach(consumer);
			}
		}
	}

	/**
	 * from multi-dimensional array creates and fills regular array of the values
	 * 
	 * @return
	 */
	public T[] flatMap() {
		List<T> intList = new ArrayList<T>();
		forEach(x -> intList.add(x));
		return toArray(intList);
	}

	/*
	 * https://stackoverflow.com/a/18137953/13285088
	 */
	public static <T> T[] toArray(final List<T> obj) {
		if (obj == null || obj.isEmpty()) {
			return null;
		}
		final T t = obj.get(0);
		final T[] res = (T[]) Array.newInstance(t.getClass(), obj.size());
		for (int i = 0; i < obj.size(); i++) {
			res[i] = obj.get(i);
		}
		return res;
	}

	/**
	 * sets a give value into MdArray as scalar throws the following exceptions
	 * Wrong Index Exception Wrong Number of indexes
	 * 
	 * @param indexes
	 * @param value
	 */
	public void setValue(int[] indexes, T value) {
		MdArray<T> scalar = getMdScalar(indexes);
		scalar.value = value;

	}

	/**
	 * gets value from MdArray as scalar throws the following exceptions Wrong Index
	 * Exception Wrong Number of indexes
	 * 
	 * @param indexes
	 *
	 */
	public T getValue(int[] indexes) {
		MdArray<T> scalar = getMdScalar(indexes);
		return scalar.value;

	}

	private MdArray<T> getMdScalar(int[] indexes) {
		try {
			MdArray<T> res = array[indexes[0]];
			for (int i = 1; i < indexes.length; i++) {
				res = res.array[indexes[i]];
			}
			if (res.array != null) {
				throw new RuntimeException("Too few indexes");
			}
			return res;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new RuntimeException("Wrong index exception " + e.getMessage());
		} catch (NullPointerException e) {
			throw new RuntimeException("Too much indexes");
		}

	}

}
