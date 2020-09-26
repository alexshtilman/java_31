package telran.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MdArray {
	private MdArray[] array; // array of MdArray objects on one dimension less if array is not null the value
								// should be null
	private Integer value; // MdArray with dimension 0 - scalar; if value is not null the array should be
							// null

	private MdArray(int[] dimensions, int dimension, Integer value) {
		if (dimension == dimensions.length) {
			array = null;
			this.value = value;
		} else {
			array = new MdArray[dimensions[dimension]];
			for (int i = 0; i < array.length; i++) {
				array[i] = new MdArray(dimensions, dimension + 1, value);
			}
		}
	}

	public MdArray(int[] dimensions, Integer value) {
		this(dimensions, 0, value);
	}

	/**
	 * performs a given action defined by Consumer functional interface for each
	 * value in multi-dimensional array
	 * 
	 * @param consumer
	 */
	public void forEach(Consumer<Integer> consumer) {
		forEachLoop(array[0],consumer);
	}
	private void forEachLoop(MdArray dimensions,Consumer<Integer> consumer) {
		if (dimensions.value!=null) {
			consumer.accept(dimensions.value);
		}
		else {
			for (int i=0;i<dimensions.array.length;i++) {
				forEachLoop(dimensions.array[i],consumer);
			}
		}
	}
	/**
	 * from multi-dimensional array creates and fills regular array of the values
	 * 
	 * @return
	 */
	public Integer[] flatMap() {
		List<Integer> intList = new ArrayList<Integer>();
		forEach(x -> intList.add(x));
		return intList.toArray(new Integer[0]);
	}

	/**
	 * sets a give value into MdArray as scalar throws the following exceptions
	 * Wrong Index Exception Wrong Number of indexes
	 * 
	 * @param indexes
	 * @param value
	 */
	public void setValue(int[] indexes, Integer value) {
		MdArray scalar = getMdScalar(indexes);
		scalar.value = value;

	}

	/**
	 * gets value from MdArray as scalar throws the following exceptions Wrong Index
	 * Exception Wrong Number of indexes
	 * 
	 * @param indexes
	 *
	 */
	public Integer getValue(int[] indexes) {
		MdArray scalar = getMdScalar(indexes);
		return scalar.value;

	}

	private MdArray getMdScalar(int[] indexes) {
		try {
			MdArray res = array[indexes[0]];
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
