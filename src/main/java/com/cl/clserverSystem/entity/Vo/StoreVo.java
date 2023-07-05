package com.cl.clserverSystem.entity.Vo;

import com.cl.clserverSystem.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: StoreVo
 * Package: com.cl.clserverSystem.entity.Vo
 * Description:
 *
 * @Author waweji
 * @Create 2023/7/5 15:07
 * @Version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StoreVo {
    private int number;
    private Store store;
}
