package com.ken.code;

import com.ken.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamTest {

	private List<User> userList;

	@Before
	public void before() {
		userList = new ArrayList<User>();
		userList.add(User.builder().code("a").name("aName").age(20).roleId(1).build());
		userList.add(User.builder().code("c").name("cName").age(25).roleId(1).build());
		userList.add(User.builder().code("b").name("bName").age(15).roleId(2).build());
	}

	@Test
	public void testGroupingBy() {

		// 聚合为Map，TreeMap::new HashMap::new
		TreeMap<Integer, Long> map1 = userList.stream().collect(Collectors.groupingBy(User::getRoleId, TreeMap::new, Collectors.counting()));
		System.out.println(map1);

		// value自定义计算
		TreeMap<Integer, Integer> map2 = userList.stream().collect(Collectors.groupingBy(User::getRoleId, TreeMap::new, Collectors.reducing(0, item -> item.getAge(), (u, ia) -> u + ia)));
		System.out.println(map2);

		// value自定义计算
		TreeMap<Integer, User> map3 = userList.stream().collect(Collectors.groupingBy(User::getRoleId, TreeMap::new, Collectors.reducing(User.builder().age(0).build(), item -> item, (u, item) -> doSomething(u, item))));
		System.out.println(map3);

		// key roleId, value: List<name>
		Map<Integer, List<String>> mapList = userList.stream().collect(Collectors.groupingBy(User::getRoleId, Collectors.mapping(User::getName, Collectors.toList())));
		System.out.println(mapList);

		// 添加age重复内容
		userList.add(User.builder().code("d").name("dName").age(15).roleId(2).build());
//		userList.stream().mapToLong(User::getAge).distinct().collect(Collectors.toMap(a -> a, a -> a));

		// 处理key重复内容（覆盖 or 拼接等自定义处理）
		Map<Integer, String> map4 = userList.stream().collect(Collectors.toMap(User::getAge, User::getName, (val1, val2) -> val1 + "-" + val2));
		System.out.println("map4:" + map4);
	}

	@Test
	public void testReduce() {

		// 数据聚合为一条数据（User返回对象是为了复用）
		User reduce = userList.stream().reduce(User.builder().age(0).build(), (u, item) -> doSomething(u, item));
		System.out.println(reduce);
	}

	@Test
	public void testDistinct() {

		// 添加重复内容
		userList.add(User.builder().code("b").name("bName").age(15).roleId(2).build());

		// 去重
		List<User> distList = userList.stream().distinct().collect(Collectors.toList());
		Assert.assertEquals(3, distList.size());

		// 按照某个属性进行去重
		List<User> resultList = this.userList.stream().filter(distinctByKey(u -> u.getRoleId())).collect(Collectors.toList());
		resultList.stream().forEach(System.out::println);
	}

	@Test
	public void testSorted() {

		// 排序 空内容处理
		List<User> sortAndLimitList = userList.stream().sorted(Comparator.nullsFirst(Comparator.comparing(User::getAge, this::compareInteger)).reversed()).collect(Collectors.toList());
		sortAndLimitList.forEach(System.out::println);
	}

	@Test
	public void testCollect() {

		// TO Map, Set, List (Collectors.toSet Or toList)
		Map<String, User> userMap = userList.stream().collect(Collectors.toMap(User::getCode, u -> u));
		List<String> codeList = userList.stream().map(User::getCode).collect(Collectors.toList());
		userMap.forEach((k, v) -> System.out.println(k + ":" + v));

		// To Map fix Duplicate key
		Map<String, User> userMapDistinct = userList.stream().collect(Collectors.toMap(u -> u.getCode(), u -> u, (v1, v2) -> v2));
		userMapDistinct.forEach((k, v) -> System.out.println(k + ":" + v));

		// Statistics: sum max age  and so on
		int sumAge1 = userList.stream().collect(Collectors.reducing(0, User::getAge, (i, j) -> i + j));
		int sumAge2 = userList.stream().collect(Collectors.reducing(0, User::getAge, Integer::sum));
		int sumAge3 = userList.stream().mapToInt(User::getAge).sum();
		int sumAge4 = userList.stream().collect(Collectors.summingInt(User::getAge));
		Assert.assertEquals(45, sumAge1);
		Assert.assertEquals(45, sumAge2);
		Assert.assertEquals(45, sumAge3);
		Assert.assertEquals(45, sumAge4);

		Optional<User> maxAgeUser = userList.stream().max(Comparator.comparingInt(User::getAge));
		Assert.assertEquals("a", maxAgeUser.get().getCode());

		int maxAge1 = userList.stream().collect(Collectors.reducing(0, User::getAge, Integer::max));
		int maxAge2 = userList.stream().mapToInt(User::getAge).max().orElse(-1);
		Assert.assertEquals(20, maxAge1);
		Assert.assertEquals(20, maxAge2);

		Double ageAge = userList.stream().collect(Collectors.averagingInt(User::getAge));
		Assert.assertEquals(15, ageAge.doubleValue(), 0.1);

		// 数值统计
		IntSummaryStatistics sta = userList.stream().collect(Collectors.summarizingInt(User::getAge));
		Assert.assertEquals(15, sta.getAverage(), 0.1);
		Assert.assertEquals(20, sta.getMax());
		Assert.assertEquals(3, sta.getCount());
		Assert.assertEquals(45, sta.getSum());
	}

	@Test
	public void testJoin() {

		String codes = userList.stream().map(User::getCode).collect(Collectors.joining("-"));
		Assert.assertEquals("a-c-b", codes);

		// Parameter join
		List<String> joinParamList = userList.stream().map(d -> d.getCode() + "-" + d.getName()).collect(Collectors.toList());
		joinParamList.forEach(System.out::println);
	}

	@Test
	public void testFilter() {

		System.out.println("Test filter -> map:推荐");
		System.out.println(this.userList.stream().filter(u -> "a".equals(u.getCode())).map(this::getCodeAndPrint).collect(Collectors.toList()));

		System.out.println("Test map -> filter:");
		System.out.println(this.userList.stream().map(this::getCodeAndPrint).filter(un -> "a".equals(un)).collect(Collectors.toList()));

		// findOne
		boolean hasSomeOne = userList.stream().anyMatch(d -> "e".equals(d.getCode()));
		Assert.assertFalse(hasSomeOne);

		Optional<User> userOptional = userList.stream().filter(u -> "a".equals(u.getCode())).findFirst();
		Assert.assertTrue(userOptional.isPresent());
	}

	private String getCodeAndPrint(User user) {
		System.out.println(user.getCode());
		return user.getCode();
	}

	// 按照某个属性进行distinct
	private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	// 比较两个参数大小
	private int compareInteger(Integer a1, Integer a2) {
		int r = 0;
		if (a1 == null) {
			r = -1;
		} else if (a2 == null) {
			r = 1;
		} else {
			r = a1.compareTo(a2);
		}
		return r;
	}

	private User doSomething(User u, User item) {
		// u参数应该为统计对象，这里不想多定义了，在这里复用
		// 各种计算 求和，求平均等等
//		System.out.println("item:" + item);
//		System.out.println("u:" + u);
		u.setAge(u.getAge() + item.getAge());
		return User.builder().age(u.getAge() + item.getAge()).build();
	}
}
