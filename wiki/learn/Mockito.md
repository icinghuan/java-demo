# Mockito

Mockito框架，能够Mock对象、验证结果以及打桩。

- Maven依赖

```
<dependency>
<groupId>org.mockito</groupId>
<artifactId>mockito-core</artifactId>
<version>2.23.4</version>
<scope>test</scope>
</dependency>
```

### 1. Verify 验证

- 验证方法是否执行过 `verify(Object).method();`

```
//Let's import Mockito statically so that the code looks clearer
import static org.mockito.Mockito.*;

//mock creation
List mockedList = mock(List.class);

//using mock object
mockedList.add("one");
mockedList.clear();

//verification
verify(mockedList).add("one");
verify(mockedList).clear();
```

- 验证执行次数 `verfiy(Object, times()/at..()).method();`

| 方法 | 作用 | 备注 |
| :-: | :-: | :-: |
| times(x) | 执行了x次 | |
| atLeast(x) | 至少执行了x次 | |
| atLeastOnce() | 至少执行了一次 | |
| atMost(x) | 至多执行了x次 | |
| never() | 从未执行过 | |
| - | 验证执行一次 | 没有第二个参数时默认为times(1) |

```
//using mock
mockedList.add("once");

mockedList.add("twice");
mockedList.add("twice");

mockedList.add("three times");
mockedList.add("three times");
mockedList.add("three times");

//following two verifications work exactly the same - times(1) is used by default
verify(mockedList).add("once");
verify(mockedList, times(1)).add("once");

//exact number of invocations verification
verify(mockedList, times(2)).add("twice");
verify(mockedList, times(3)).add("three times");

//verification using never(). never() is an alias to times(0)
verify(mockedList, never()).add("never happened");

//verification using atLeast()/atMost()
verify(mockedList, atLeastOnce()).add("three times");
verify(mockedList, atLeast(2)).add("three times");
verify(mockedList, atMost(5)).add("three times");
```

- InOrder 验证执行顺序

```
// A. Single mock whose methods must be invoked in a particular order
 List singleMock = mock(List.class);

 //using a single mock
 singleMock.add("was added first");
 singleMock.add("was added second");

 //create an inOrder verifier for a single mock
 InOrder inOrder = inOrder(singleMock);

 //following will make sure that add is first called with "was added first", then with "was added second"
 inOrder.verify(singleMock).add("was added first");
 inOrder.verify(singleMock).add("was added second");

 // B. Multiple mocks that must be used in a particular order
 List firstMock = mock(List.class);
 List secondMock = mock(List.class);

 //using mocks
 firstMock.add("was called first");
 secondMock.add("was called second");

 //create inOrder object passing any mocks that need to be verified in order
 InOrder inOrder = inOrder(firstMock, secondMock);

 //following will make sure that firstMock was called before secondMock
 inOrder.verify(firstMock).add("was called first");
 inOrder.verify(secondMock).add("was called second");

 // Oh, and A + B can be mixed together at will
```

- verifyZeroInteractions 验证对象间没有交互

```
//using mocks - only mockOne is interacted
mockOne.add("one");

//ordinary verification
verify(mockOne).add("one");

//verify that method was never called on a mock
verify(mockOne, never()).add("two");

//verify that other mocks were not interacted
verifyZeroInteractions(mockTwo, mockThree);
```

- verifyNoMoreInteractions 查找冗余的调用

```
//using mocks
mockedList.add("one");
mockedList.add("two");

verify(mockedList).add("one");

//following verification will fail
verifyNoMoreInteractions(mockedList);
```

### 2. stub 打桩

** Mock对象 **

在对象前添加@Mock注解来简化mock对象的创建

`MockitoAnnotations.initMocks(testClass);`需要在运行测试函数前调用，一般放到测试类的基类或是@Before中

** 打桩 **

- `when(Object.method()).then...();`

| 方法 | 作用 | 备注 |
| :--: | :--: | :--: |
| thenReturn() | mock返回值 | |
| thenThrow() | mock返回异常 | |
| thenAnswer() | mock执行一系列方法 | 实现`invocation -> {...}`|
| thenCallRealMethod() | 执行真正的方法 | | |

```
//You can mock concrete classes, not just interfaces
LinkedList mockedList = mock(LinkedList.class);

//stubbing
when(mockedList.get(0)).thenReturn("first");
when(mockedList.get(1)).thenThrow(new RuntimeException());

//following prints "first"
System.out.println(mockedList.get(0));

//following throws runtime exception
System.out.println(mockedList.get(1));

//following prints "null" because get(999) was not stubbed
System.out.println(mockedList.get(999));

//Although it is possible to verify a stubbed invocation, usually it's just redundant
//If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
//If your code doesn't care what get(0) returns, then it should not be stubbed. Not convinced? See here.
verify(mockedList).get(0);
```
thenAnswer中需要实现new Answer()
```
when(mock.someMethod(anyString())).thenAnswer(
     new Answer() {
         public Object answer(InvocationOnMock invocation) {
             Object[] args = invocation.getArguments();
             Object mock = invocation.getMock();
             return "called with arguments: " + Arrays.toString(args);
         }
 });

 //Following prints "called with arguments: [foo]"
 System.out.println(mock.someMethod("foo"));
 ```

- `do..().when(Object).method();`

| 方法 | 作用 | 备注 |
| :--: | :--: | :--: |
| doReturn() | mock返回值 | |
| doThrow() | mock返回异常 | |
| doAnswer() | mock执行一系列方法 | 实现`invocation -> {...}`|
| doCallRealMethod() | 执行真正的方法 | |
| doNothing() | 什么也不做 | | |

```
doThrow(new RuntimeException()).when(mockedList).clear();

//following throws RuntimeException:
mockedList.clear();
```

do系列方法完全可以替代when系列方法

以下三种情况只能使用do方法：
1. 在void方法上打桩
2. 在spy对象上打桩
3. 为了在测试过程中改变mock对象的行为而多次打桩同一方法


- 参数匹配器

Mockito通过equals来验证参数值，也可以通过参数匹配器`method(any...())`来匹配参数

| 方法 | 作用 | 备注 |
| :--: | :--: | :--: |
| any(Object.class) | 匹配Object类型 | 不带参数时等同于anyObject() |
| anyObject() | 匹配任何类型 | |
| anyInt() | 匹配Integer类型 | |
| anyString() | 匹配String类型 | |
| ... | ... | |
| eq() | 等于 | |
| argThat() | 自定义匹配器 | | |

```
//stubbing using built-in anyInt() argument matcher
when(mockedList.get(anyInt())).thenReturn("element");

//stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
when(mockedList.contains(argThat(isValid()))).thenReturn("element");

//following prints "element"
System.out.println(mockedList.get(999));

//you can also verify using an argument matcher
verify(mockedList).get(anyInt());

//argument matchers can also be written as Java 8 Lambdas
verify(mockedList).add(argThat(someString -> someString.length() > 5));
```
注意：如果使用参数匹配器,所有参数都必须由匹配器提供。
```
verify(mock).someMethod(anyInt(), anyString(), eq("third argument"));
//above is correct - eq() is also an argument matcher

verify(mock).someMethod(anyInt(), anyString(), "third argument");
//above is incorrect - exception will be thrown because third argument is given without an argument matcher.
```

- 为连续调用做测试桩

```
when(mock.someMethod("some arg"))
  .thenThrow(new RuntimeException())
  .thenReturn("foo");

//First call: throws runtime exception:
mock.someMethod("some arg");

//Second call: prints "foo"
System.out.println(mock.someMethod("some arg"));

//Any consecutive call: prints "foo" as well (last stubbing wins).
System.out.println(mock.someMethod("some arg"));
```
或是`when(mock.someMethod("some arg")).thenReturn("one", "two", "three");`

注意：不使用链式调用的写法，后面的桩会覆盖前面的
```
//All mock.someMethod("some arg") calls will return "two"
when(mock.someMethod("some arg"))
  .thenReturn("one")
when(mock.someMethod("some arg"))
  .thenReturn("two")
```

### 3. spy 监控

spy用于监控真实的对象，当调用spy对象时真实对象也会被调用(除非它的函数被stub了)。
```
List list = new LinkedList();
List spy = spy(list);

//optionally, you can stub out some methods:
when(spy.size()).thenReturn(100);

//using the spy calls *real* methods
spy.add("one");
spy.add("two");

//prints "one" - the first element of a list
System.out.println(spy.get(0));

//size() method was stubbed - 100 is printed
System.out.println(spy.size());

//optionally, you can verify
verify(spy).add("one");
verify(spy).add("two");
```
在spy对象上打桩尽量使用do系列方法，用when系列方法时真实的方法也会被调用，可能会抛出异常
```
List list = new LinkedList();
List spy = spy(list);

//Impossible: real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)
when(spy.get(0)).thenReturn("foo");

//You have to use doReturn() for stubbing
doReturn("foo").when(spy).get(0);
```
Mockito并不会为真实对象代理函数调用，实际上它会拷贝真实对象。因此如果你保留了真实对象并且与之交互，不要期望从监控对象得到正确的结果。当你在监控对象上调用一个没有被stub的函数时并不会调用真实对象的对应函数，你不会在真实对象上看到任何效果。


### Mockito的其他特性

- 修改没有测试桩的调用的默认返回值(1.7版本之后)

```
Foo mock = mock(Foo.class, Mockito.RETURNS_SMART_NULLS);
Foo mockTwo = mock(Foo.class, new YourOwnAnswer());
```

-  为下一步的断言捕获参数(1.8版本之后)

```
ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
// 参数捕获
verify(mock).doSomething(argument.capture());
// 使用equal断言
assertEquals("John", argument.getValue().getName());
```

- 新的注解:@Captor|@Spy|@InjectMocks(1.8.3版本之后)


1. @Captor 简化 ArgumentCaptor 的创建 - 当需要捕获的参数是一个令人讨厌的通用类，而且你想避免编译时警告。
2. @Spy - 你可以用它代替 spy(Object) 方法
3. @InjectMocks - 自动将模拟对象或侦查域注入到被测试对象中。需要注意的是 @InjectMocks 也能与 @Spy 一起使用，这就意味着 Mockito 会注入模拟对象到测试的部分测试中。

和@Mock一样，`MockitoAnnotations.initMocks(testClass);`需要在运行测试函数前调用

- 验证超时(1.8.5版本之后)
```
//passes when someMethod() is called within given time span
 verify(mock, timeout(100)).someMethod();
 //above is an alias to:
 verify(mock, timeout(100).times(1)).someMethod();

 //passes when someMethod() is called *exactly* 2 times within given time span
 verify(mock, timeout(100).times(2)).someMethod();

 //passes when someMethod() is called *at least* 2 times within given time span
 verify(mock, timeout(100).atLeast(2)).someMethod();

 //verifies someMethod() within given time span using given verification mode
 //useful only if you have your own custom verification modes.
 verify(mock, new Timeout(100, yourOwnVerificationMode)).someMethod();
 ```

- mock详情(1.9.5版本之后)

MockingDetails.isMock()和 MockingDetails.isSpy()方法都会返回一个布尔值，用于区别一个对象是模拟对象还是侦查对象。


---

参考：

[mockito官方文档2.23.4](https://static.javadoc.io/org.mockito/mockito-core/2.23.4/org/mockito/Mockito.html)

[mockito中文文档2.0.26 beta](https://github.com/hehonghui/mockito-doc-zh)
