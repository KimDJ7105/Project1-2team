# FDS — 금융 이상거래탐지 서비스

멋쟁이사자처럼 클라우드 엔지니어링 부트캠프 1차 팀 프로젝트. 일반적인 은행 서비스(계좌 개설, 입출금, 송금, 거래 내역 조회)를 구현하고, 여기에 이상거래탐지(FDS) 기능을 얹어 거래 내역 조회 시 검사 결과를 함께 보여준다. Kubernetes 클러스터에 실제로 배포까지 진행했다.

## 팀 구성 및 역할

- **김동재** (본인): 백엔드·프론트엔드 개발 총괄
- **조윤지**: 프론트엔드 설계 및 개발 참여
- **안효재**: 백엔드 개발, ER 설계
- **양지훈**: Docker 관련 작업 및 관리

## 기술 스택

- 백엔드: Spring Boot(Java), MyBatis, Gradle
- 프론트엔드: Thymeleaf(서버 사이드 렌더링)
- 인프라: Kubernetes(StatefulSet, PV/PVC, HPA, Ingress, Secret), Docker

## 프로젝트 구조

| 디렉터리 | 역할 |
|---|---|
| `project1/` | Spring Boot 애플리케이션 본체 (controller/service/mapper/dto/exception) |
| `k8s/` | Kubernetes 매니페스트 (StatefulSet DB, PV/PVC, HPA, Ingress, Secret) |

## 주요 기능

- 회원가입/로그인, 계좌 개설, 입출금, 송금, 거래 내역 조회
- 거래 내역 조회 시 FDS(이상거래탐지) 검사 결과를 함께 표시 — 판별 기준은 공개된 FDS 관련 자료를 참고해 팀에서 자체 설계
- Kubernetes StatefulSet 기반 DB, HPA 오토스케일링, Ingress/Secret 분리까지 실제 클러스터에 배포

## 실행 방법

### Docker Compose로 실행 (로컬)

```bash
cd project1
docker compose up --build
```

MySQL 컨테이너와 앱 컨테이너가 함께 뜨며, 기본적으로 `localhost:8080`에서 접속할 수 있다.

### 직접 실행

`application.yml` 기준 아래 환경변수가 필요하다.

- `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`

```bash
cd project1
./gradlew bootRun
```

### Kubernetes 배포

`k8s/` 디렉터리의 매니페스트를 순서대로 apply한다 (`namespace.yaml` → `secret.yaml` → `DB-Statfulset.yaml`/`PV.yaml`/`PVC.yaml` → `finance-deployment.yaml`/`finance-svc.yaml` → `hpa.yaml` → `ingress.yaml`).

## 시연 영상

레포에 포함된 `앱 시연 영상 최종 (음악x).mp4` 참고.
