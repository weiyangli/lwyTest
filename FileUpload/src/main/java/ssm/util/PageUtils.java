package ssm.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * MySQL 的分页和 MongoDB 不同:
 *     MySQL: 使用 offset + size，需要用 pageNumber+size 计算 offset
 *     Mongo: 使用 pageNumber + size，直接使用 pageNumber 即可
 *
 * MySQL 分页时需要计算某一页的起始位置，或则使用记录总数计算共有多少页:
 *     PageUtils.offset(pageNumber, pageSize) 用于计算起始位置
 *     PageUtils.pageCount(recordCount, pageSize) 用于计算共有多少页
 *
 * MongoDB 的分页对象使用方法 pageRequestOf 创建
 *     PageUtils.pageRequestOf(pageNumber, pageSize, sort)
 */
public final class PageUtils {
    /**
     * 根据传入的页数、每页上的最多记录数计算这一页面的开始位置 offset，最小为 0.
     *
     * @param pageNumber 页码 (从 1 开始)
     * @param pageSize   每页上的最多记录数
     * @return 开始的位置 offset
     */
    public static int offset(int pageNumber, int pageSize) {
        // 校正参数，pageNumber 从 1 开始，pageSize 最小为 1
        pageNumber = Math.max(1, pageNumber);
        pageSize   = Math.max(1, pageSize);

        int offset = (pageNumber-1) * pageSize; // 计算此页开始的位置 offset
        return offset;
    }

    /**
     * 根据传入的记录总数、每页上的最多记录数计算总页数 pageCount，最小为 1 页.
     *
     * @param recordCount 记录总数
     * @param pageSize    每页上的最多记录数
     * @return 总页数
     */
    public static int pageCount(int recordCount, int pageSize) {
        // 校正参数，recordCount 最小为 0，pageSize 最小为 1
        recordCount = Math.max(0, recordCount);
        pageSize    = Math.max(1, pageSize);

        int page = (recordCount-1) / pageSize + 1;
        return page;
    }

    /**
     * 使用页码、数量和排序规则创建 MongoDB 的分页对象
     *
     * @param pageNumber 页码 (从 1 开始)
     * @param pageSize   每页上的最多记录数
     * @return 返回 MongoDB 的分页对象
     */
    public static PageRequest pageRequestOf(int pageNumber, int pageSize) {
        return PageUtils.pageRequestOf(pageNumber, pageSize, Sort.unsorted());
    }

    /**
     * 使用页码、数量和排序规则创建 MongoDB 的分页对象
     *
     * @param pageNumber 页码 (从 1 开始)
     * @param pageSize   每页上的最多记录数
     * @param sort       排序对象
     * @return 返回 MongoDB 的分页对象
     */
    public static PageRequest pageRequestOf(int pageNumber, int pageSize, Sort sort) {
        // 注: 我们习惯页码从第 1 页开始，而 mongoTemplate 的页码从 0 开始
        pageNumber = pageNumber >= 1 ? pageNumber - 1 : 0; // 页码从 0 开始
        pageSize   = pageSize > 0 ? pageSize : 1;          // 最少取一条记录

        return PageRequest.of(pageNumber, pageSize, sort);
    }
}
