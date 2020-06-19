const operatorRouter = [
    {
        path: '/operator/manageOrder',
        name: 'manageOrder',
        component: () => import('@/views/operator/manageOrder')
    },
    {
        path: '/operator/creditList',
        name: 'creditList',
        component: () => import('@/views/operator/creditList')
    },
    {
        path: '/operator/msgForGroup',
        name: 'msgForGroup',
        component: () => import('@/views/operator/msgForGroup'),
    },
];

export default operatorRouter

