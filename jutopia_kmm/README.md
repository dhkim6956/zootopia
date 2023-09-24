# Kotlin Multiplatform Mobile 개발

## Kotlin Multiplatform

- 단일 프로그래밍 언어 (kotlin)로 멀티플랫폼 코드를 작성하는 개발방식

## 프레임워크

- compose multiplatform
  - jetpack compose를 kmm에서도 사용할 수 있도록 만든 프레임워크
  - JetBrain 에서 개발
  - [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform)
  - [Compose Multiplatform Wizard](https://terrakok.github.io/Compose-Multiplatform-Wizard/)
  - 지원 플랫폼

|iOS|Android|Desktop|Web|
|:---:|:---:|:---:|:---:|
|`Alpha`|Jetpack Compose|Windows, MacOS, Linux|`Experimental`|

> 현재 **네비게이션 기능**은 Android 플랫폼에서만 지원한다 ([링크](https://github.com/JetBrains/compose-multiplatform/tree/master/tutorials/Navigation))


## 라이브러리

- PreCompose
  - compose multiplatform 에서 사용 불가능한 네비게이션 기능을 구현한 라이브러리
  - [PreCompose](https://github.com/Tlaster/PreCompose)
  - [네비게이션 가이드](https://tlaster.github.io/PreCompose/component/navigation.html)

- kmp awesome
  - kmm 호환 라이브러리 모음 깃 허브
  - [kmp awesome](https://github.com/terrakok/kmp-awesome#-compose-ui)


## 참고 사이트

- Font
  - [Common 에서 사용가능한 FontFamily 제작](https://jassielcastro.medium.com/custom-fonts-in-android-and-ios-applications-using-kotlin-multiplatform-and-jetpack-compose-c88d2d519e77)