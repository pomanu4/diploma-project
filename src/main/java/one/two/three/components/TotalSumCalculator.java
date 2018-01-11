package one.two.three.components;

import java.util.Collection;
import org.springframework.stereotype.Component;

import one.two.three.entity.SimpleOrder;

@Component
public class TotalSumCalculator {
	
	public int findSumm(Collection<SimpleOrder> coll) {
		int summ = 0;
		
		for( SimpleOrder simOrd : coll) {
			summ = summ + (simOrd.getHowMany() * simOrd.getProduct().getPrice());
		}
		return summ;
	}

}
