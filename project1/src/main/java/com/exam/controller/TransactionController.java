package com.exam.controller;

import com.exam.dto.AccountDTO;
import com.exam.dto.FdsDTO;
import com.exam.dto.TradingHistoryDTO;
import com.exam.dto.UserDTO;
import com.exam.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TransactionController {

    TransactionService transactionService;
    TradingHistoryService tradingHistoryService;
    AccountService accountService;
    UserService userService;
    FdsService fdsService;

   public TransactionController(TransactionService transactionService,
                             TradingHistoryService tradingHistoryService,
                                AccountService accountService,
                                UserService userService,
                                FdsService fdsService) {

    this.transactionService = transactionService;
    this.tradingHistoryService = tradingHistoryService;
    this.accountService = accountService;
    this.userService = userService;
    this.fdsService = fdsService;
}

    // 계좌 이체 화면 요청
    @GetMapping("/sendMoney")
    public String sendMoney(@RequestParam(value = "accountId", required = false) int accountId, Model model) {

        TradingHistoryDTO dto = new TradingHistoryDTO();

        if (accountId > 0) {
            dto.setSendingAccount(accountId);
        } else {
            // 제대로된 값이 넘어오지 않은 경우.
            //todo 예외 처리 필요.
        }

        model.addAttribute("tradingHistoryDTO", dto);

        return "transfer";
    }

    // 계좌 이체 처리
    @PostMapping("/sendMoney")
    public String sendMoneyProcess(TradingHistoryDTO tradingHistoryDTO, Model model, RedirectAttributes redirectAttributes) {

        // 가져온 데이터 : 보내는 계좌 id, 목표 계좌 번호, 금액, 메시지

        tradingHistoryDTO.setTradeType("송금");
        
        // 송금 서비스 실행
        FdsDTO fdsDTO = transactionService.sendMoneyProcess(tradingHistoryDTO);

        // 송금 실패
        if (fdsDTO.getFdsId() == -1) {
            model.addAttribute("errorMessage", tradingHistoryDTO.getFailMsg());
            model.addAttribute("tradingHistoryDTO", tradingHistoryDTO);
            return "transfer";
        }

        // FDS 위험 감지 플래그 작동
        if (fdsDTO.getRiskScore() >= 40 && !tradingHistoryDTO.isFdsBypass()) {
            model.addAttribute("fdsRisk", true);
            model.addAttribute("errorMessage", "이상 거래 위험이 감지되었습니다. 안전을 위해 5초 후 송금이 가능합니다.");
            model.addAttribute("fdsResultDTO", fdsDTO);
            model.addAttribute("tradingHistoryDTO", tradingHistoryDTO);
            return "transfer";
        }

        AccountDTO myAccount =
                accountService.findAccountById(tradingHistoryDTO.getSendingAccount());

        redirectAttributes.addFlashAttribute("tradingHistoryDTO", tradingHistoryDTO);
        redirectAttributes.addFlashAttribute("myAccountNumber", myAccount.getAccountNumber());
        redirectAttributes.addFlashAttribute("currentBalance", myAccount.getBalance() - tradingHistoryDTO.getAmount());

        return "redirect:/transferComplete";

    }

    // 입금 화면 요청
    @GetMapping("/deposit")
    public String depositMoney(@RequestParam(value = "accountId", required = false) int accountId, Model model) {

        TradingHistoryDTO dto = new TradingHistoryDTO();

        if (accountId > 0) {
            dto.setSendingAccount(accountId);
        } else {
            // 제대로된 값이 넘어오지 않은 경우.
            //todo 예외 처리 필요.
        }

        model.addAttribute("tradingHistoryDTO", dto);

        return "deposit";
    }

    // 입금 처리
    @PostMapping("/deposit")
    public String depositProcess(TradingHistoryDTO tradingHistoryDTO, Model model, RedirectAttributes redirectAttributes) {

        // 가져온 데이터 : 보내는 계좌 id, 금액, 메시지

        if (tradingHistoryDTO.getAmount() <= 0) {
            model.addAttribute("errorMessage", "입금할 금액을 정확히 입력해 주세요.");
            model.addAttribute("tradingHistoryDTO", tradingHistoryDTO);
            return "deposit";
        }

        // 계좌 정보 로드
        AccountDTO myAccount = accountService.findAccountById(tradingHistoryDTO.getSendingAccount());
        if (myAccount == null) {
            model.addAttribute("errorMessage", "존재하지 않는 계좌이거나 접근이 거부되었습니다.");
            model.addAttribute("tradingHistoryDTO", tradingHistoryDTO);
            return "deposit";
        }

        // 입금 처리
        HashMap<String,Object> depositMap = new HashMap<>();
        depositMap.put("accountNumber",myAccount.getAccountNumber());
        depositMap.put("amount",tradingHistoryDTO.getAmount());
        accountService.deposit(depositMap);

        tradingHistoryDTO.setTradeType("입금");
        tradingHistoryDTO.setSendingAccount(myAccount.getAccountId());
        tradingHistoryDTO.setReceivingAccount(myAccount.getAccountId());
        if(tradingHistoryDTO.getMessage() == null || tradingHistoryDTO.getMessage().equals("")){
            tradingHistoryDTO.setMessage(userService.findUser(
                    myAccount.getUserId())
                            .getName());
        }
        tradingHistoryDTO.setIsFail(false);

        tradingHistoryService.insertTradingHistory(tradingHistoryDTO);

        // 입금 성공
        redirectAttributes.addFlashAttribute("tradingHistoryDTO", tradingHistoryDTO);
        redirectAttributes.addFlashAttribute("myAccountNumber", myAccount.getAccountNumber());
        redirectAttributes.addFlashAttribute("currentBalance", myAccount.getBalance() + tradingHistoryDTO.getAmount());

        return "redirect:/transferComplete";

    }

    // 입금 화면 요청
    @GetMapping("/withdraw")
    public String withdrawMoney(@RequestParam(value = "accountId", required = false) int accountId, Model model) {

        TradingHistoryDTO dto = new TradingHistoryDTO();

        if (accountId > 0) {
            dto.setSendingAccount(accountId);
        } else {
            // 제대로된 값이 넘어오지 않은 경우.
            //todo 예외 처리 필요.
        }

        model.addAttribute("tradingHistoryDTO", dto);

        return "withdraw";
    }

    // 입금 처리
    @PostMapping("/withdraw")
    public String withdrawProcess(TradingHistoryDTO tradingHistoryDTO, Model model, RedirectAttributes redirectAttributes) {

        // 가져온 데이터 : 보내는 계좌 id, 금액, 메시지

        if (tradingHistoryDTO.getAmount() <= 0) {
            model.addAttribute("errorMessage", "입금할 금액을 정확히 입력해 주세요.");
            model.addAttribute("tradingHistoryDTO", tradingHistoryDTO);
            return "deposit";
        }

        // 계좌 정보 로드
        AccountDTO myAccount = accountService.findAccountById(tradingHistoryDTO.getSendingAccount());
        if (myAccount == null) {
            model.addAttribute("errorMessage", "존재하지 않는 계좌이거나 접근이 거부되었습니다.");
            model.addAttribute("tradingHistoryDTO", tradingHistoryDTO);
            return "deposit";
        }

        // 출금 처리
        HashMap<String,Object> withdrawMap = new HashMap<>();
        withdrawMap.put("accountNumber",myAccount.getAccountNumber());
        withdrawMap.put("amount",tradingHistoryDTO.getAmount());
        accountService.withdraw(withdrawMap);

        tradingHistoryDTO.setTradeType("출금");
        tradingHistoryDTO.setSendingAccount(myAccount.getAccountId());
        tradingHistoryDTO.setReceivingAccount(myAccount.getAccountId());
        if(tradingHistoryDTO.getMessage() == null || tradingHistoryDTO.getMessage().equals("")){
            tradingHistoryDTO.setMessage(userService.findUser(
                            myAccount.getUserId())
                    .getName());
        }
        tradingHistoryDTO.setIsFail(false);

        tradingHistoryService.insertTradingHistory(tradingHistoryDTO);

        // 출금 성공
        redirectAttributes.addFlashAttribute("tradingHistoryDTO", tradingHistoryDTO);
        redirectAttributes.addFlashAttribute("myAccountNumber", myAccount.getAccountNumber());
        redirectAttributes.addFlashAttribute("currentBalance", myAccount.getBalance());

        return "redirect:/transferComplete";

    }

    // 거래내역 조회
    @GetMapping("/history")
    public String historyPage(@RequestParam(value = "accountId", required = false) Integer accountId,
                              HttpSession session, Model model) {

        //유저의 고유 식별자
        UserDTO loginUser = (UserDTO) session.getAttribute("myLogin");
        if (loginUser == null) {
            return "redirect:/home"; // 프로젝트의 로그인 혹은 메인 주소 경로에 맞게 지정하세요
        }
        int userId = loginUser.getUserId();

        // 실제 계좌 리스트
        List<AccountDTO> accountList = accountService.findAccount(userId);
        model.addAttribute("accountList", accountList);

        //accountId가 없다면 리스트의 첫 번째 대표 계좌를 기본값으로 지정
        if (accountId == null && !accountList.isEmpty()) {
            accountId = accountList.get(0).getAccountId();
        }

        // 타겟 계좌의 정상 거래 내역들만 필터링해서 토스
        List<TradingHistoryDTO> list = tradingHistoryService.tradingHistory(accountId);
        List<TradingHistoryDTO> successList = list.stream()
                .filter(dto -> !dto.isFail())
                .collect(Collectors.toList());

        for(TradingHistoryDTO l : list) {
            FdsDTO dto = fdsService.findFdsByHistory(l.getHistoryId());
            if(dto == null) {
                l.setMessage("정상");
            }
            else {
                l.setMessage(dto.getRiskRank());
            }
        }

        model.addAttribute("historyList", successList);
        model.addAttribute("currentAccountId", accountId);

        return "transactionHistory";
    }

    // 거래 완료 페이지
    @GetMapping("/transferComplete")
    public String transferComplete(Model model) {
        return "transferComplete";
    }
}
