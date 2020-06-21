const msgRouter = [
    {
        path: '/:userType/chat',
        name: 'chat',
        component: () => import('@/views/chat/ChatBox'),
    },

];

export default msgRouter;
