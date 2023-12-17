# Chapter 13: Buffer

## 1. Buffer 개요

- Buffer는 OS가 관리하는 system memory를 직접 사용할 수 있다
- 기존 방식
  - Buffer 대신 byte[] 를 사용해서 JVM의 heap 영역에 memory가 할당되고, byte[] 의 초기값이 system memory로 한 번 `복사`되어야 했다
  - 복사로 인한 성능 저하 뿐만 아니라, OS 수준에서 제공하는 기능 (e.g. DMA, Virtual memory, mmap) 을 사용할 수 없는 점이 가장 큰 단점이었다
- nio에서 달라진 점
  - C/C++에서 pointer를 사용하는 것처럼 Java에서도 직접 system memory에 접근할 수 있게 Buffer class가 추가됐다

<p align="center" width="100%">
  <img width="500" alt="Buffers" src="https://github.com/slippStudy/java-network-study/assets/53922851/f5ce38e4-e977-41eb-9a8c-d422b306ad4e">
</p>

- `ByteOrder`
  - Buffer, 즉 memory의 byte 순서 조정을 위해 사용되는데, Java는 기본적으로 big endian 방식을 사용하므로 byte 순서에 대한 고려가 없기 때문에 거의 사용되지 않는다
- `Buffer`
  - 자신을 상속하는 하위 class들에서 공통적으로 사용할 method를 정의한 부모 class다
  - Buffer를 하나의 data 형태를 저장하는 컨테이너라고 할 수 있다
  - 시작과 끝이 있는 일직선 모양의 데이터 구조를 갖고, 내부적으로 자신의 상태 정보를 네 가지 기본 속성 값에 저장한다

## 2. Buffer의 4가지 기본 속성

```java
// Invariants: mark <= position <= limit <= capacity
private int mark = -1;
private int position = 0;
private int limit;
private final int capacity;
```

- `position`
  - 읽거나 쓸 위치 값
- `limit`
  - 읽거나 쓸 수 있는 한계 값
- `capacity`
  - buffer의 크기
- `mark`
  - 현재의 position을 표시해 둘  때 사용 (mark() method 사용)

<aside>
✅ Buffer 계열 class들은 체이닝을 하도록 디자인되어 있다
이유는 체이닝이 코드량을 줄여주고, 이해하기 쉽게 해주기 때문이다

</aside>

## 3. Buffer에서 데이터 읽고 쓰기

Buffer class를 상속하는 모든 하위 class들은 다양한 형식으로 데이터를 읽고 / 쓸 수 있는 method를 제공한다

1. `상대적` 위치를 이용해서 1바이트씩 읽고 쓰기
2. `절대적` 위치를 이용해서 1바이트씩 읽고 쓰기
3. 배열을 이용해서 한꺼번에 많은 데이터를 읽고 쓰기
4. 버퍼 자체를 파라미터로 받아서 쓰기

(예제 생략)

## 4. Buffer class가 제공하는 utility method

Buffer가 제공하는 여러 utility method를 이용해 Buffer를 쉽게 사용할 수 있다

1. `clear()`
    - buffer의 각 속성 (e.g. position, limit)을 buffer가 생성될 때의 초기값으로 만든다
    - but, buffer의 내용은 변화가 없고, `각 속성의 값만 초기화` 시킨다
        - why?
            - buffer 내용까지 초기화 시키지 않아도 buffer를 재사용할 수 있기 때문이다!
2. `rewind()`
    - position 속성만 초기화 시킨다
        - 해당 method를 호출하기 전에 `mark` 값이 설정되어 있었다면, mark 값도 초기화 (-1) 시킨다
    - 읽었던 데이터를 `다시 읽기 위해 사용` 되는 method 다
3. `flip()`
    - limit을 position이 있던 위치로 설정한 후에, position을 0으로 이동시킨다
        - 해당 method를 호출하기 전에 `mark` 값이 설정되어 있었다면, mark 값도 초기화 시킨다
    - 사용 예시
        - 쓰기 작업 완료 후 읽기 모드로 전환 (어디까지 썼는지 기억할 수 있으므로)

### 이미 사용한 Buffer를 재사용하기

- `clear()` method를 호출
- 데이터를 buffer에 쓰기 (`put()` method 사용)
- `flip()` method로 재사용할 범위 지정 후 buffer에서 읽어오기

## 5. Buffer 하위 utility method

1. `compact()`
    - 동작 방식
        - position에서 limit 사이에 남아있는 데이터를 buffer의 맨 앞부터 순서대로 쓴다
        - 쓴 위치만큼 position을 이동시킨다
        - limit은 buffer가 생성되는 시점 (capacity와 동일하게) 으로 만든다
        - 해당 method를 호출하기 전에 mark 값이 설정되어 있었다면, mark 값도 초기화 시킨다
    - 사용 예
        - buffer 안의 데이터를 남김 없이 모두 전송하고 싶을 때 유용하다
        - buffer에서 읽은 데이터를 쓰기 위한 준비를 할 때 사용
2. `duplicate()`
    - buffer에 복사본을 생성할 때 사용한다
    - 복사본은 원본 buffer와 같은 memory 공간을 참조하는 buffer를 생성한다 (`shallow copy`)
3. `asReadOnlyBuffer()`
    - duplicate() method로 생성한 buffer와 다른것은 다 동일하지만, 읽기만 가능하다
        - `put(`) method 호출로 수정 불가
4. `slice()`
    - 설명
        - buffer의 `일부분`만 복사할 때 사용한다
        - 원본 buffer의 position에서 limit 까지만 떼어내어 새로운 buffer를 생성한다
    - duplicate(), asReadOnlyBuffer()와 다른점
        - slice()로 생성된 buffer는 각각의 기본 속성이 초기화된다

## 6. Buffer 만들기

(예제 생략)

## 7. ByteBuffer

다른 buffer들과 차별화되는 ByteBuffer만의 특징은, ByteBuffer가 system memory를 직접 사용하는 `direct buffer`를 만들 수 있는 유일한 buffer class 라는 것이다

### 7-1. Direct Buffer

- `ByteBuffer.allocateDirect()` method로 direct buffer를 사용했을 때 일어나는 일
  - method parameter로 주어진 크기만큼의 system memory를 JNI를 이용해서 `할당`한다
  - 이 system memory를 `wrapping하는 java 객체`를 만들어 리턴한다

        → 해당 객체를 이용하여 직접적으로 system memory를 제어할 수 있고, 변경 사항이 바로 system memory에 반영된다

- Direct Buffer가 garbage collect 되면,
  - 해당 buffer가 wrapping하고 있는 주소의 system memory도 동시에 안전하게 deallocate 된다
- 유의할 점
  - channel (저수준 IO) 의 target이 되는 것은 `direct buffer` 뿐이라서, `non-direct buffer`를 사용하면 성능이 저하된다
    - non-direct buffer를 target으로 사용했을 때 일어나는 일
            1. non-direct buffer를 channel에 전달
            2. 임시로 사용할 direct buffer 생성
            3. non-direct buffer에서 임시로 만든 direct buffer로 데이터를 복사
            4. 임시 buffer를 사용해서 channel이 저수준 IO 실행
            5. 임시 buffer가 다 사용되면, 임시 buffer 를 GC

### 7-2. View Buffer

- ViewBuffer를 사용하여 channel을 통해 읽어들인 데이터를 원하는 형태로 쉽게 변형해서 다룰 수 있다
- 변형된 buffer를 수정하면 ByteBuffer도 변경되므로, Byte가 아닌 다른 데이터 형식을 사용하는 것이 더 유리할 때 사용할 수 있다

## 8. CharBuffer

CharBuffer는 String을 이용해서 읽기 전용 View buffer를 만들 수 있는 method와 String을 buffer에 쉽게 읽고 쓸 수 있는 기능을 제공한다

<aside>
✅ 모든 buffer class API를 보면 method에 `synchronzied` keyword가 없는 것을 알 수 있다.

그 이유는 buffer가 데이터를 효율적으로 읽고 쓰기 위한 것이라서 속도에 영향을 주는 synchronized keyword로 method를 동기화하지 않았다.

그러므로 동기화 문제가 발생할 여지가 있는 곳에서는 개발자가 동기화 해줘야 한다!

</aside>
