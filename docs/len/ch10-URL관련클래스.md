# URL 관련 클래스



- URL - Uniform Resource Locator. 인터넷에서 접근 가능한 자원의 주소를 일관되게 표현할 수 있는 형식
- 자원의 형태는 인터넷 응용 프로토콜에 따라 다를 수 있다. WWW인 HTTP를 사용하는 경우의 자원에서는 HTML 페이지, 이미지 파일



http://sunny.sarang.net/sunny



## URL 클래스

**URL 사용 목적**은?

URL 주소가 알맞은 형식의 URL 주소인지 검사하거나, URL 주소로부터 사용되는 프로토콜, 서버명, 포트, 파일명 등을 구할 때 사용. 또는 URL 주소로 지정된 파일을 읽어 들일 때 사용

**URL클래스를 이용한 URL 주소 분석 - URLInfo**

URL 클래스를 이용해서 URL 주소를 분석하는 프로그램

```java
class URLInfo {

    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("https://ceo.baemin.com/");

        System.out.println("프로토콜: " + url.getProtocol());
        System.out.println("호스트명: " + url.getHost());
        System.out.println("포트번호: " + url.getPort());
        System.out.println("파일명: " + url.getPath());
        System.out.println("사용자 쿼리: " + url.getQuery());

    }
}
```

### URL 클래스를 이용해서 웹사이트 읽기

```java
URL url = new URL("https://ceo.baemin.com/");
InputStream inputStream = url.openStream();
FileOutputStream fos = new FileOutputStream("index.html");
```

OpenStream 을 통해 가져올 수 있다.



## URLConnection 클래스로 웹 페이지 읽기

POST 는 웹 애플리케이션에 추가적인 정보를 전달해야 할 때 사용한다.

## GET 방식으로 URL 주소 호출하기

## POST 방식으로 URL 주소 호출하기

## URLEncoder 를 이용한 문자열 변환

## URLEncoder 클래스를 이용한 디코딩









