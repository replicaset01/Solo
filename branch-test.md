# branch-test

## 브랜치 생성
```
git switch -c feature
git checkout -b feature
```

## 브랜치 변경
기존에 있던 브랜치로 HEAD를 변경
```
git switch feature
git checkout feature
```

## 브랜치 합치기(merge)
개발 진행 후 커밋
```
git commit -m "message"
...
```

커밋 후 푸쉬
```
git push origin feat/todo
```

변경사항 pull request

feature 브렌치 커밋 푸쉬
- 피드백을 받을 수 있게 자주 커밋 푸쉬하는 것이 좋음.

dev 브랜치
- 다음 버전 배포를 위한 개발중인 브랜치

main(or master) 브랜치
- 사용자에게 언제든 제품으로 출시할 수 있는 브랜치