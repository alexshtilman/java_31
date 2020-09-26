package telran.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.MdArray;

class MdArrayTests {
	MdArray<Integer> mdArray;

	@BeforeEach
	void setUp() throws Exception {
		int[] dimensions = { 1, 2, 3 };
		/**
		 * [
		 * 		[1]->
		 * 			[
		 * 				[0,1]->
		 * 					[
		 * 						[0]->
		 * 							[
		 * 								[0,1,2]->
		 * 										[
		 *  										[0]-> value = 42
		 *  										[1]-> value = 42  
		 *    										[2]-> value = 42		
		 *   									]
		 * 							]
		 * 						[1]->
		 * 							[
		 * 								[0,1,2]->
		 * 		 								[
		 *  										[0]-> value = 42
		 *  										[1]-> value = 42  
		 *    										[2]-> value = 42		
		 *   									]
		 * 							]
		 * 					]
		 * 			]
		 * ]
		 * 
		 */
		Integer initValue = 42;
		mdArray = new MdArray(dimensions, initValue);
	}
	@Test
	void testSetValue() {
		int[][] indexes = {
							{0,0,0},{0,0,1},{0,0,2},
							{0,1,0},{0,1,1},{0,1,2}
						  };
		int[] values = {1,2,3,4,5,6};
		int i=0;
		for(int[]index:indexes) {
			mdArray.setValue(index, values[i++]);
		}
		i=0;
		for(int[]index:indexes) {
			assertEquals(mdArray.getValue(index), values[i++]);	
		}
		try {
			int [] index = {0,0,5};
			mdArray.setValue(index,-99);
            fail();
		} catch (RuntimeException e) {
			//Expected exception
		}
		try {
			int [] index = {0,0};
			mdArray.setValue(index,-99);
            fail();
		} catch (RuntimeException e) {
			//Expected exception
		}
		try {
			int [] index = {0,0,0,5};
			mdArray.setValue(index,-99);
            fail();
		} catch (RuntimeException e) {
			//Expected exception
		}
	}
	@Test
	void testGetValue() {
		int[][] indexes = {
				{0,0,0},{0,0,1},{0,0,2},
				{0,1,0},{0,1,1},{0,1,2}
			  };
		for(int[]index:indexes) {
			assertEquals(mdArray.getValue(index), 42);	
		}
		try {
			int [] index = {0,0,5};
			mdArray.getValue(index);
            fail();
		} catch (RuntimeException e) {
			//Expected exception
		}
		try {
			int [] index = {0,0};
			mdArray.getValue(index);
            fail();
		} catch (RuntimeException e) {
			//Expected exception
		}
		try {
			int [] index = {0,0,0,5};
			mdArray.getValue(index);
            fail();
		} catch (RuntimeException e) {
			//Expected exception
		}
	}
	@Test
	void testForEach() {
		mdArray.forEach(a ->assertEquals(a,42));
	}

	@Test
	void testFlatMap() {
		Integer[] maped =  mdArray.flatMap();
		for(Integer i:maped) {
			assertEquals(i,42);
		}
	}



  
}
