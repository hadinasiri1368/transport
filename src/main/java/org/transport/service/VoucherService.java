package org.transport.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.transport.common.CommonUtils;
import org.transport.model.Voucher;
import org.transport.model.VoucherDetail;
import org.transport.repository.JPA;

import java.util.Date;
import java.util.List;

@Service
public class VoucherService {
    @Autowired
    private JPA<Voucher, Long> genericVoucherJPA;
    @Autowired
    private JPA<VoucherDetail, Long> genericVoucherDetailJPA;
    @Autowired
    private EntityManager entityManager;

    @Value("${PageRequest.page}")
    private Integer page;
    @Value("${PageRequest.size}")
    private Integer size;

    @Transactional
    public void insert(Voucher voucher, List<VoucherDetail> voucherDetails, Long userId) throws Exception {
        voucher.setId(null);
        voucher.setInsertedUserId(userId);
        voucher.setInsertedDateTime(new Date());
        genericVoucherJPA.save(voucher);
        for (VoucherDetail voucherDetail : voucherDetails) {
            voucherDetail.setId(null);
            voucherDetail.setVoucher(voucher);
            voucherDetail.setInsertedUserId(userId);
            voucherDetail.setInsertedDateTime(new Date());
            genericVoucherDetailJPA.save(voucherDetail);
        }
        checkVoucher(voucher.getId());
    }

    @Transactional
    public void insert(Long voucherId, List<VoucherDetail> voucherDetails, Long userId) throws Exception {
        Voucher voucher = findOne(voucherId);
        for (VoucherDetail voucherDetail : voucherDetails) {
            voucherDetail.setId(null);
            voucherDetail.setVoucher(voucher);
            voucherDetail.setInsertedUserId(userId);
            voucherDetail.setInsertedDateTime(new Date());
            genericVoucherDetailJPA.save(voucherDetail);
        }
        checkVoucher(voucher.getId());
    }

    public void update(Voucher voucher, Long userId) throws Exception {
        voucher.setUpdatedUserId(userId);
        voucher.setUpdatedDateTime(new Date());
        genericVoucherJPA.update(voucher);
    }

    @Transactional
    public void updateVoucherDetail(VoucherDetail voucherDetail, Long userId) throws Exception {
        voucherDetail.setUpdatedUserId(userId);
        voucherDetail.setUpdatedDateTime(new Date());
        genericVoucherDetailJPA.update(voucherDetail);
        checkVoucher(voucherDetail.getVoucher().getId());
    }

    @Transactional
    public void updateVoucherDetail(Long voucherId, List<VoucherDetail> voucherDetails, Long userId) throws Exception {
        for (VoucherDetail voucherDetail : voucherDetails) {
            voucherDetail.setUpdatedUserId(userId);
            voucherDetail.setUpdatedDateTime(new Date());
            genericVoucherDetailJPA.update(voucherDetail);
        }
        checkVoucher(voucherId);
    }

    @Transactional
    public void delete(Voucher voucher) {
        for (VoucherDetail voucherDetail : voucher.getVoucherDetails()) {
            genericVoucherDetailJPA.remove(voucherDetail);
        }
        genericVoucherJPA.remove(voucher);
    }

    @Transactional
    public void deleteVoucherDetail(VoucherDetail voucherDetail) {
        genericVoucherDetailJPA.remove(voucherDetail);
        checkVoucher(voucherDetail.getVoucher().getId());
    }

    @Transactional
    public void deleteVoucherDetail(Long voucherId, List<VoucherDetail> voucherDetails) {
        for (VoucherDetail voucherDetail : voucherDetails) {
            genericVoucherDetailJPA.remove(voucherDetail);
        }
        checkVoucher(voucherId);
    }

    @Transactional
    public void deleteVoucherDetail(List<Long> voucherDetailIds, Long voucherId) {
        int returnValue = entityManager.createQuery("delete from voucherDetail where voucherDetail.id in (:voucherDetailIds)")
                .setParameter("voucherDetailIds", voucherDetailIds).executeUpdate();
        checkVoucher(voucherId);
    }

    @Transactional
    public void deleteVoucherDetail(Long voucherId, Long voucherDetailId) {
        entityManager.createQuery("delete from voucherDetail where voucherDetail.id=" + voucherDetailId).executeUpdate();
        checkVoucher(voucherId);
    }

    @Transactional
    public int delete(Long voucherId) {
        entityManager.createQuery("delete from voucherDetail entity where entity.voucher.id=:voucherId").setParameter("voucherId", voucherId).executeUpdate();
        return entityManager.createQuery("delete from voucher entity where entity.id=:voucherId").setParameter("voucherId", voucherId).executeUpdate();
    }

    public Voucher findOne(Long id) {
        return genericVoucherJPA.findOne(Voucher.class, id);
    }

    public List<Voucher> findAll(Class<Voucher> aClass) {
        return genericVoucherJPA.findAll(aClass);
    }

    private void checkVoucher(Long voucherId) {
        Query query = entityManager.createQuery("select entity from voucherDetail entity where entity.voucher.id=:voucherId");
        query.setParameter("voucherId", voucherId);
        List<VoucherDetail> voucherDetails = (List<VoucherDetail>) query.getResultList();
        long sumCredit = 0, sumDebit = 0;
        for (VoucherDetail voucherDetail : voucherDetails) {
            if (CommonUtils.isNull(voucherDetail.getDetailLedger()))
                throw new RuntimeException("2023");
            if (CommonUtils.isNull(voucherDetail.getDebitAmount(), 0L) == 0L && CommonUtils.isNull(voucherDetail.getCreditAmount(), 0L) == 0L)
                throw new RuntimeException("2024");
            if (CommonUtils.isNull(voucherDetail.getDebitAmount(), 0L) != 0L && CommonUtils.isNull(voucherDetail.getCreditAmount(), 0L) != 0L)
                throw new RuntimeException("2025");
            sumDebit += CommonUtils.isNull(voucherDetail.getDebitAmount(), 0L);
            sumCredit += CommonUtils.isNull(voucherDetail.getCreditAmount(), 0L);
        }
        if (sumDebit != sumCredit)
            throw new RuntimeException("2026");
    }

    public Page<Voucher> findAll(Class<Voucher> aClass, Integer page, Integer size) {
        if (CommonUtils.isNull(page) && CommonUtils.isNull(size)) {
            return genericVoucherJPA.findAllWithPaging(aClass);
        }
        PageRequest pageRequest = PageRequest.of(CommonUtils.isNull(page, this.page), CommonUtils.isNull(size, this.size));
        return genericVoucherJPA.findAllWithPaging(aClass, pageRequest);
    }

}
