const msgRouter = [
    {
        path: '/user/opMessage',
        name: 'opChatList',
        component: () => import('@/views/chat/msgFromOp'),
    },
    {
        path: '/operator/userMessage',
        name: 'userChatList',
        component: () => import('@/views/chat/msgFromUser'),
    },
    {
        path: '/:userType/chat',
        name: 'chat',
        component: () => import('@/views/chat/components/ChatBox'),
    },

];

export default msgRouter;
